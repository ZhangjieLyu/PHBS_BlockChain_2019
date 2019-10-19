
import java.security.PublicKey;

public class BlockHandler {
    private BlockChain blockChain;

    /** assume blockChain has the genesis block */
    public BlockHandler(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    /**
     * add {@code block} to the block chain if it is valid.
     * 
     * @return true if the block is valid and has been added, false otherwise
     */
    public boolean processBlock(Block block) {
        if (block == null)
            return false;
        return blockChain.addBlock(block);
    }

    public boolean processBlockManualFork(Block block, int goBackHeight){
        if(block==null)
            return false;
        return blockChain.addBlockManualFork(block, goBackHeight);
    }

    /** create a new {@code block} over the max height {@code block} */
    public Block createBlock(PublicKey myAddress) {
        Block parent = blockChain.getMaxHeightBlock();
        byte[] parentHash = parent.getHash();
        Block current = new Block(parentHash, myAddress);
        UTXOPool uPool = blockChain.getMaxHeightUTXOPool();
        TransactionPool txPool = blockChain.getTransactionPool();
        TxHandler handler = new TxHandler(uPool);
        Transaction[] txs = txPool.getTransactions().toArray(new Transaction[0]);
        Transaction[] rTxs = handler.handleTxs(txs);

        for (int i = 0; i < rTxs.length; i++)
            current.addTransaction(rTxs[i]);

        current.finalize();
        if (blockChain.addBlock(current)) {
            System.out.println("Size of transaction pool is: " + blockChain.getTransactionPool().getTransactions().size());
            System.out.println("-----------------------------");
            return current;
        }else {
            return null;
        }
    }

    /** create a new {@code block} over the max height - goBack height {@code block} */
    public Block createManualForkingBlock(PublicKey myAddress, int goBackHeight) {
        Block parent = blockChain.getGoBackBlock(goBackHeight);
        byte[] parentHash = parent.getHash();
        Block current = new Block(parentHash, myAddress);
        UTXOPool uPool = blockChain.getMaxHeightUTXOPool();
        TransactionPool txPool = blockChain.getTransactionPool();
        TxHandler handler = new TxHandler(uPool);
        Transaction[] txs = txPool.getTransactions().toArray(new Transaction[0]);
        Transaction[] rTxs = handler.handleTxs(txs);
        for (int i = 0; i < rTxs.length; i++)
            current.addTransaction(rTxs[i]);

        current.finalize();
        boolean status = blockChain.addBlockManualFork(current, goBackHeight);
        System.out.println(status);
        if (status) {
            System.out.println("Size of transaction pool is: " + blockChain.getTransactionPool().getTransactions().size());
            System.out.println("-----------------------------");
            return current;
        }else {
            return null;
        }
    }

    /** process a {@code Transaction} */
    public void processTx(Transaction tx) {
        blockChain.addTransaction(tx);
    }
}
