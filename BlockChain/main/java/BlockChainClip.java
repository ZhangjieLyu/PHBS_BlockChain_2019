import java.util.ArrayList;

import static java.lang.System.out;

public class BlockChainClip {
    private Block block;
    private int height;
    private UTXOPool utxoPool;
    private BlockChain.BlockNode parentNode;
    private ArrayList<BlockChain.BlockNode> childNodes;
    private BlockChain.BlockNode blockNode;
    public BlockChainClip(BlockChain.BlockNode blockNode){
        this.block = blockNode.getNode_block();
        this.height = blockNode.getNode_height();
        this.utxoPool = blockNode.getNode_UTXOPool();
        this.parentNode = blockNode.getNode_parentNode();
        this.childNodes = blockNode.getNode_childNodes();
        this.blockNode = blockNode;
    }

    public void printBlockChainClip(){
        BlockChain.BlockNode currentBlockNode = this.blockNode;
        printDFSSearch(currentBlockNode);
    }

    private void printDFSSearch(BlockChain.BlockNode blockNode){
        if(blockNode.getNode_childNodes().size() == 0){
            out.println("Block hash is: " + blockNode.getNode_block().hashCode());
            out.println("Side Branch End!");
        }else{
            for(BlockChain.BlockNode childBlockNode:blockNode.getNode_childNodes()){
                out.println("Block hash is: "+blockNode.getNode_block().hashCode());
                printDFSSearch(childBlockNode);
            }
        }
    }
}
