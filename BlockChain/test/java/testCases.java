import org.junit.Test;

import javax.management.openmbean.ArrayType;

import static org.junit.Assert.assertTrue;

import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class testCases {
    /**
     * This function is to initialize participants in a block chain
     * @param numberOfUsers
     * @return ArrayList, element consists of key pairs
     */
    public ArrayList<KeyPair> initKeyPairs(int numberOfUsers) throws NoSuchAlgorithmException {
        ArrayList<KeyPair> userArray = new ArrayList<KeyPair>();
        for(int i=0; i<numberOfUsers; i++){
            userArray.add(KeyPairGenerator.getInstance("RSA").generateKeyPair());
        }
        return userArray;
    }

    /**
     * init a transaction, with one in one out.
     */
    public Transaction initTx_oneInOneOut(byte[] prevTxHash, int prevOutputIndex, int coins, int inputIndex,
                                          PublicKey receiverAddress, PrivateKey senderPrivateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Transaction tx = new Transaction();
        tx.addInput(prevTxHash, prevOutputIndex);
        tx.addOutput(coins, receiverAddress);
        tx.signTx(senderPrivateKey,inputIndex);
        return tx;
    }

    /**
     * Test case 1 A valid block chain and store limited length of block chain
     * To show a normal block chain.
     * 1. create valid fork
     * 2. utxo pool is maintained by each block
     * 3. transaction pool is maintained in a global view
     * 4. if automatically add block, block will be added to the longest chain
     */
    @Test
    public void testCase1() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //init ten players in the block chain
        ArrayList<KeyPair> users = initKeyPairs(10);
        //init genesis block
        Block genesisBlock = new Block(null, users.get(0).getPublic());
        genesisBlock.finalize();
        //init block chain and corresponding block handler
        BlockChain blockChain = new BlockChain(genesisBlock);
        BlockHandler blockHandler = new BlockHandler(blockChain);

        //create block 1,M, processBlock
        // init tx
        Transaction tx0 = initTx_oneInOneOut(genesisBlock.getCoinbase().getHash(),0,
                10,0,users.get(1).getPublic(),users.get(0).getPrivate());
        // init block
        Block block1M = new Block(genesisBlock.getHash(), users.get(1).getPublic());
        block1M.addTransaction(tx0);
        block1M.finalize();
        // add block to the block chain
        blockHandler.processBlock(block1M);

        //block 1,B, processBlockManualFork
        // init tx
        Transaction tx1 = initTx_oneInOneOut(genesisBlock.getCoinbase().getHash(),0,
                20,0,users.get(2).getPublic(),users.get(0).getPrivate());
        // init block
        Block block1B = new Block(genesisBlock.getHash(), users.get(2).getPublic());
        block1B.addTransaction(tx1);
        block1B.finalize();
        // add to block chain
        blockHandler.processBlockManualFork(block1B, 1);

        // block 2,M, processBlock
        Block block2M = new Block(block1M.getHash(), users.get(2).getPublic());
        block2M.finalize();
        // add to block chain
        blockHandler.processBlock(block2M);

        // block 3,M, processBlock
        Block block3M = new Block(block2M.getHash(), users.get(3).getPublic());
        block3M.finalize();
        // add to block chain
        blockHandler.processBlock(block3M);

        // block 3,B. processBlockManualFork
        Block block3B = new Block(block2M.getHash(), users.get(4).getPublic());
        block3B.finalize();
        // add to block chain
        blockHandler.processBlockManualFork(block3B, 1);

        // block 3,B, processBlockManualFork
        Block block3BB = new Block(block2M.getHash(), users.get(5).getPublic());
        block3BB.finalize();
        // add to block chain
        blockHandler.processBlockManualFork(block3BB, 1);

        // block 4,M, processBlockManualFork
        Block block4M = new Block(block3M.getHash(), users.get(4).getPublic());
        block4M.finalize();
        // add to block chain
        blockHandler.processBlock(block4M);

        blockChain.printBlockChain();

        // store partial block chain
        // set store height = 2, means height to store = 2(exclude the last block node)
        BlockChainClip blockChainClip = blockChain.getNode_recentNodes(2,blockChain);
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
        System.out.println("store a limited length of block chain:");
        blockChainClip.printBlockChainClip();
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
    }



    /**
     * Test case 2 Illegal coinbase
     * 1.use current block's coinbase in current block
     * 2.use an excessive number of coinbase
     */
    @Test
    public void testCase2() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //init ten players in the block chain
        ArrayList<KeyPair> users = initKeyPairs(10);
        //init genesis block
        Block genesisBlock = new Block(null, users.get(0).getPublic());
        genesisBlock.finalize();
        //init block chain and corresponding block handler
        BlockChain blockChain = new BlockChain(genesisBlock);
        BlockHandler blockHandler = new BlockHandler(blockChain);

        //create block 1,M, processBlock, use current block's coinbase in current block
        // init block
        Block block1M = new Block(genesisBlock.getHash(), users.get(1).getPublic());
        // init tx
        Transaction tx0 = initTx_oneInOneOut(block1M.getCoinbase().getHash(),0,
                10,0,users.get(1).getPublic(),users.get(0).getPrivate());
        block1M.addTransaction(tx0);
        block1M.finalize();
        // add block to the block chain
        blockHandler.processBlock(block1M);

        blockChain.printBlockChain();
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");


        // create block 1,M, processBlock, use an excessive number of coinbase
        // init block
        Block block2M = new Block(genesisBlock.getHash(), users.get(1).getPublic());
        // init tx
        Transaction tx1 = initTx_oneInOneOut(block1M.getCoinbase().getHash(),0,
                40,0,users.get(1).getPublic(),users.get(0).getPrivate());
        block2M.addTransaction(tx1);
        block2M.finalize();
        // add block to the block chain
        blockHandler.processBlock(block2M);

        blockChain.printBlockChain();
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
    }

    /**
     * Test case 3 Illegal manual fork
     * 1.fork at a valid position, the CUT_OFF_AGE is reset to be 2 for convenience.
     */
    @Test
    public void testCase3() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //init ten players in the block chain
        ArrayList<KeyPair> users = initKeyPairs(10);
        //init genesis block
        Block genesisBlock = new Block(null, users.get(0).getPublic());
        genesisBlock.finalize();
        //init block chain and corresponding block handler
        BlockChain blockChain = new BlockChain(genesisBlock);
        BlockHandler blockHandler = new BlockHandler(blockChain);

        //create block 1,M, processBlock
        // init tx
        Transaction tx0 = initTx_oneInOneOut(genesisBlock.getCoinbase().getHash(), 0,
                10, 0, users.get(1).getPublic(), users.get(0).getPrivate());
        // init block
        Block block1M = new Block(genesisBlock.getHash(), users.get(1).getPublic());
        block1M.addTransaction(tx0);
        block1M.finalize();
        // add block to the block chain
        blockHandler.processBlock(block1M);

        // block 2,M, processBlock
        Block block2M = new Block(block1M.getHash(), users.get(2).getPublic());
        block2M.finalize();
        // add to block chain
        blockHandler.processBlock(block2M);

        // block 3,M, processBlock
        Block block3M = new Block(block2M.getHash(), users.get(3).getPublic());
        block3M.finalize();
        // add to block chain
        blockHandler.processBlock(block3M);

        // block 4,M, processBlockManualFork
        Block block4M = new Block(block3M.getHash(), users.get(4).getPublic());
        block4M.finalize();
        // add to block chain
        blockHandler.processBlock(block4M);

        // block 5,M, attempt to fork at an invalid position
        Block block5M = new Block(block2M.getHash(), users.get(8).getPublic());
        block5M.finalize();
        // add to block chain
        blockHandler.processBlockManualFork(block5M,3);

        blockChain.printBlockChain();
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
    }

    /**
     * Test case 4 Illegal previous block hash
     * 1. insert a new genesis block when there exists a genesis block in the block chain;
     * 2. incorrect previous hash when using automatic forking.
     */
    @Test
    public void testCase4() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //init ten players in the block chain
        ArrayList<KeyPair> users = initKeyPairs(10);
        //init genesis block
        Block genesisBlock = new Block(null, users.get(0).getPublic());
        genesisBlock.finalize();
        //init block chain and corresponding block handler
        BlockChain blockChain = new BlockChain(genesisBlock);
        BlockHandler blockHandler = new BlockHandler(blockChain);

        //init 2rd genesis block
        Block genesisBlock2 = new Block(null, users.get(0).getPublic());
        genesisBlock2.finalize();

        //create block 1,M, processBlock, use current block's coinbase in current block
        // init block
        Block block1M = new Block(genesisBlock.getHash(), users.get(1).getPublic());
        // init tx
        Transaction tx0 = initTx_oneInOneOut(block1M.getCoinbase().getHash(),0,
                10,0,users.get(1).getPublic(),users.get(0).getPrivate());
        block1M.addTransaction(tx0);
        block1M.finalize();
        // add block to the block chain
        blockHandler.processBlock(block1M);

        // create a second genesis block
        blockHandler.processBlock(genesisBlock2);

        // block 2,M, processBlock,incorrect previous hash when using automatic forking.
        Block block2M = new Block(genesisBlock.getHash(), users.get(2).getPublic());
        block2M.finalize();
        // add to block chain
        blockHandler.processBlock(block2M);

        blockChain.printBlockChain();
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
    }

}
