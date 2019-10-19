// Block Chain should maintain only limited block nodes to satisfy the functions
// You should not have all the blocks added to the block chain in memory 
// as it would cause a memory overflow.

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.out;

public class BlockChain {
    public static final int CUT_OFF_AGE = 2; // reset for convenience of test
    //public static final int CUT_OFF_AGE = 10;
    private BlockNode genesisBlockNode;
    private TransactionPool globalTxPool; //a block collect transactions from Transaction Pool

    /**
     * create an empty block chain with just a genesis block. Assume {@code genesisBlock} is a valid
     * block
     */
    public BlockChain(Block genesisBlock) {
        // IMPLEMENT THIS
        // if the block chain is not null, return false.
        this.genesisBlockNode = new BlockNode(genesisBlock);
        this.globalTxPool = new TransactionPool();

        // add coinbase to UTXO pool
        Transaction coinbaseTx = genesisBlock.getCoinbase();
//        byte[] txHash = new byte[]{};
        UTXO utxo = new UTXO(coinbaseTx.getHash(), 0);

        this.genesisBlockNode.height = 1;
        this.genesisBlockNode.utxoPool.addUTXO(utxo, coinbaseTx.getOutput(0));
        this.globalTxPool.addTransaction(coinbaseTx);
    }

    /**
     * A new sub class attached to class 'block', this sub class is to maintain tree structure
     * This sub class only maintains UTXO Pool status of each block node, as well as the transaction pool
     */
    public class BlockNode {
        private Block block;
        private int height;
        private UTXOPool utxoPool;
        private BlockNode parentNode;
        private ArrayList<BlockNode> childNodes;

        /**
         * This is a constructor
         */
        public BlockNode(Block block, int height, UTXOPool utxoPool,
                          BlockNode parentNode, ArrayList<BlockNode> childNodes) {
            this.block = block;
            this.height = height;
            this.utxoPool = utxoPool;
            this.parentNode = parentNode;
            this.childNodes = childNodes;
        }

        /**
         * This is another constructor
         */
        public BlockNode(Block block) {
            this.block = block;
            this.height = 1;
            this.childNodes = new ArrayList<BlockNode>();
            this.parentNode = null;
            this.utxoPool = new UTXOPool();
        }

        /**
         * from Block Node get corresponding attributes
         * @return corresponding class of variables
         */
        public UTXOPool getNode_UTXOPool() {
            return this.utxoPool;
        }

        public BlockNode getNode_parentNode() {
            return this.parentNode;
        }

        public int getNode_height() {
            return this.height;
        }

        public Block getNode_block() {
            return this.block;
        }

        public ArrayList<BlockNode> getNode_childNodes() {
            return this.childNodes;
        }

        /**
         * Add a block node object to a given parent node
         * @param parentNode the parent block node
         * @param block the new block to add
         * @return boolean true if success, otherwise false
         */
        public boolean addBlock(BlockNode parentNode, Block block) {
            if (block.getPrevBlockHash() == null ||
                    !new ByteArrayWrapper(block.getPrevBlockHash()).equals(new ByteArrayWrapper(parentNode.getNode_block().getHash()))) {
                return false;
            }
//            out.println("pass1");

            UTXOPool currentUTXOPool = new UTXOPool(parentNode.getNode_UTXOPool());
            TxHandler childTxHandler = new TxHandler(currentUTXOPool);
            Transaction[] childTxs = block.getTransactions().toArray(new Transaction[block.getTransactions().size()]);
            Transaction[] childValidTxs = childTxHandler.handleTxs(childTxs);

            if (childValidTxs.length != block.getTransactions().size()) {
                return false;
            }
//            out.println("pass2");

            if(parentNode.getNode_height()+1<=getNode_maxHeight(parentNode).getNode_height()-CUT_OFF_AGE){
                return false;
            }
//            out.println("pass3");

            BlockNode childNode = new BlockNode(block);
//            out.println("success create");
            childNode.parentNode = parentNode;
            parentNode.childNodes.add(childNode);
            childNode.utxoPool = childTxHandler.getUTXOPool();
            childNode.utxoPool.addUTXO(new UTXO(block.getCoinbase().getHash(),0), block.getCoinbase().getOutput(0));
            childNode.height = parentNode.getNode_height() + 1;
            out.println("Number of childNodes is: "+parentNode.getNode_childNodes().size());
            out.println("This is layer " + childNode.height + " in the block chain");
            out.println("Size of UTXO pool is: "+childNode.getNode_UTXOPool().getAllUTXO().size());
            out.println("#Txs in the block is: "+childNode.getNode_block().getTransactions().size());
            out.println("Block hash code is: "+childNode.getNode_block().hashCode());
            out.println("Size of transaction pool is: " + getTransactionPool().getTransactions().size());
            out.println("-----------------------------");
            return true;

        }
    }

    /**
     * Print tree structure
     */
    public void printBlockChain(){
        BlockNode currentBlockNode = this.genesisBlockNode;
        printDFSSearch(currentBlockNode);
//        }
    }

    private void printDFSSearch(BlockNode blockNode){
        if(blockNode.getNode_childNodes().size() == 0){
            out.println("Block hash is: " + blockNode.getNode_block().hashCode());
            out.println("Side Branch End!");
        }else{
            for(BlockNode childBlockNode:blockNode.getNode_childNodes()){
                out.println("Block hash is: "+blockNode.getNode_block().hashCode());
                printDFSSearch(childBlockNode);
            }
        }
    }

    /**
     * Get the longest branch from a given block node.
     * @param blockNode a claimed block node
     * @return the last block node in the longest branch
     */
    private BlockNode getNode_maxHeight(BlockNode blockNode) {
        if (blockNode.childNodes.isEmpty()) {
            return blockNode;
        } else {
            int nodeHeight = blockNode.getNode_height();
            for (BlockNode chileNode : blockNode.getNode_childNodes()) {
                BlockNode tempNode = getNode_maxHeight(chileNode);
                if (tempNode.getNode_height() > nodeHeight) {
                    nodeHeight = tempNode.getNode_height();
                    blockNode = tempNode;
                }
            }
            return blockNode;
        }
    }

    /**
     * Get the recent nodes, to avoid overflow.
     */
    public BlockChainClip getNode_recentNodes(int storeHeight, BlockChain blockChain){
        BlockNode currentBlockNode = blockChain.getNode_maxHeight(blockChain.genesisBlockNode);
        for(int i=0; i<storeHeight; i++){
            currentBlockNode = currentBlockNode.getNode_parentNode();
        }
        BlockChainClip recentNodes_blockChain = new BlockChainClip(currentBlockNode);
        return recentNodes_blockChain;
    }

    /**
     *  Get the maximum height - go back height block
     */
    public Block getGoBackBlock(int goBackHeight){
        BlockNode maxHeightBlockNode = getNode_maxHeight(this.genesisBlockNode);
        for(int i=0; i<goBackHeight; i++){
            maxHeightBlockNode = maxHeightBlockNode.getNode_parentNode();
        }
        return maxHeightBlockNode.getNode_block();
    }

    /**
     * Get the maximum height - go back height block node
     * @param goBackHeight
     * @return Block node
     */
    private BlockNode getGoBackBlockNode(int goBackHeight){
        BlockNode maxHeightBlockNode = getNode_maxHeight(this.genesisBlockNode);
        for(int i=0; i<goBackHeight; i++){
            maxHeightBlockNode = maxHeightBlockNode.getNode_parentNode();
        }
        return maxHeightBlockNode;
    }

    /** Get the maximum height block */
    public Block getMaxHeightBlock() {
        // IMPLEMENT THIS
        return getNode_maxHeight(this.genesisBlockNode).getNode_block();
    }

    /** Get the UTXOPool for mining a new block on top of max height block */
    public UTXOPool getMaxHeightUTXOPool() {
        // IMPLEMENT THIS
        return getNode_maxHeight(this.genesisBlockNode).getNode_UTXOPool();
    }

    /** Get the transaction pool to mine a new block */
    public TransactionPool getTransactionPool() {
        // IMPLEMENT THIS
        return this.globalTxPool;
    }

    /**
     * Add {@code block} to the block chain if it is valid. For validity, all transactions should be
     * valid and block should be at {@code height > (maxHeight - CUT_OFF_AGE)}.
     * 
     * <p>
     * For example, you can try creating a new block over the genesis block (block height 2) if the
     * block chain height is {@code <=
     * CUT_OFF_AGE + 1}. As soon as {@code height > CUT_OFF_AGE + 1}, you cannot create a new block
     * at height 2.
     * 
     * @return true if block is successfully added
     */
    public boolean addBlock(Block block) {
        // IMPLEMENT THIS
        // get max height node
        BlockNode maxHeightBlockNode = getNode_maxHeight(this.genesisBlockNode);
//        out.println("block node?"+maxHeightBlockNode.getNode_block().hashCode());

        if(block.getPrevBlockHash()==null && this.genesisBlockNode != null){
            return false;
        }

        if(!new ByteArrayWrapper(block.getPrevBlockHash()).equals(
                new ByteArrayWrapper(maxHeightBlockNode.getNode_block().getHash()))){
            return false;
        }

        // to change
//        boolean status = maxHeightBlockNode.addBlock(maxHeightBlockNode, block);
//        System.out.println("status is:"+status);
        if(maxHeightBlockNode.addBlock(maxHeightBlockNode, block)){
            for(Transaction tx:block.getTransactions()){
                this.globalTxPool.removeTransaction(tx.getHash());
            }
            globalTxPool.addTransaction(block.getCoinbase());
            return true;
        }else{
            return false;
        }
    }

    /**
     * An extended version of add block
     * @param block
     * @return
     */
    public boolean addBlockManualFork(Block block, int goBackHeight) {
        // IMPLEMENT THIS
        if(goBackHeight == 0){
            return false;
        }

        BlockNode parentBlockNode = getGoBackBlockNode(goBackHeight);
//        System.out.println("pass1");

        if(block.getPrevBlockHash()==null && this.genesisBlockNode != null){
            return false;
        }
//        System.out.println("pass2");

        boolean status = parentBlockNode.addBlock(parentBlockNode, block);
//        out.println("is valid block?"+status);
        if(status){
            for(Transaction tx:block.getTransactions()){
                this.globalTxPool.removeTransaction(tx.getHash());
            }
            globalTxPool.addTransaction(block.getCoinbase());
            return true;
        }
        return false;
    }

    /** Add a transaction to the transaction pool */
    public void addTransaction(Transaction tx) {
        // IMPLEMENT THIS
        this.globalTxPool.addTransaction(tx);
    }
}