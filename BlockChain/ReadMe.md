# Block Chain
This assignment is to build a petty blcok chain, where no actual distributed consensus mechanism is introduced(though simulated).      There will be 2 parts, the 1st part is about how this petty block chain works while the 2nd part is about the test cases and corresponding explanation.

## Mechanism
The block chain in this assignment is organized in a tree, every block is enclosed in a node and transations in each node is stored in a list instead of a Merkle tree. To be specific, each block and its block node contains following components:

```java
public class Block{
  public static final int COINBASE = 25
  private byte[] prevBlockHash;
  private Transaction coinbase;
  private ArrayList<Transaction> txs;
  
  /**this is a constructor*/
  private Block(byte[] prevBlockHash, PublicKey address){
    this.prevBlockHash = prevBlockHash;
    this.coinbase = new Transaction(COINBASE, address);
    this.txs = new ArrayList<Transactions>();
  }
}

public class BlockNode{
  private UTXOPool utxoPool;
  private Block block;
  private BlockNode parentNode;
  private ArrayList<BlockNode> childNodes;
  private int height;
  
  /**This is a constructor*/
  private BlockNode(Block block){
    this.block = block;
    this.utxoPool = new UTXOPool();
    this.parentNode = new BlockNode();
    this.childNodes = new ArrayList<BlockNode>();
    this.height = height;
  }
}
```

When block chain are constructed, they're connected with *BlockNode* object, thus, it must been ensured that *parentNode.block.getHash()* is equal to *prevBlockHash*; and all *Transaction* object in *ArrayList<Transaction>* are valid, this is realized by the *TxHandler(UTXOPool)* class in **ScroogeCoin**.
  
Especially, when forking, the following rules are applied:
1. Default adding block will be attached to the longest side branch;
2. Manual forking allows users to make a new fork appended to any block whose height is not smaller than *height of current longest side branch - 10*;
3. When a fork has same-length side branches, the longest side branch is the oldest one(here realized by BFS);
4. Since only one global transaction pool is maintained, thus forking may produce irreversable changes.
