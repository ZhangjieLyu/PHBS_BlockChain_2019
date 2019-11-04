import java.util.ArrayList;
import java.util.Arrays;

public class BlockChain {
    /**
     * Block Chain is the structure of a chain, it should contain a genesis block
     * Add block will extend a block chain automatically
     * For the aim of experiment, we introduce manual fork function
     * More explanation on genesis block; genesis block is special, because it has no valid miners/other users based on our rule
     * in genesis block, prevBlockHash = null; events is empty, miner is arbitrary;
     * its height is 1, it removal list is empty, only has entry list.
     */
    private BlockNode genesisBlockNode;

    public BlockChain(Block genesisBlock, ArrayList<ExternalOwnedUser> entryEOAs,
                      ArrayList<PersonalFileAccount> entryP_accounts){
        this.genesisBlockNode = new BlockNode(genesisBlock);
        this.genesisBlockNode.setEoa_accounts(entryEOAs);
        this.genesisBlockNode.setP_accounts(entryP_accounts);
    }

    // get max height node
    public BlockNode getMaxHeightBlockNode(BlockNode blockNode){
        if(blockNode.getChildNodes().isEmpty()){
            return blockNode;
        }else{
            int blockHeight = blockNode.getHeight();
            for(BlockNode childBlockNode:blockNode.getChildNodes()){
                BlockNode tempNode = getMaxHeightBlockNode(childBlockNode);
                if(tempNode.getHeight()>blockHeight){
                    blockHeight = tempNode.getHeight();
                    blockNode = tempNode;
                }
            }
        }
        return blockNode;
    }

    public boolean automaticExtendBlockChain(Block block,
                                             ArrayList<ExternalOwnedUser> entryEOAs, ArrayList<ExternalOwnedUser> removeEOAs,
                                             ArrayList<PersonalFileAccount> entryP_accounts,ArrayList<PersonalFileAccount> removeP_accounts){
        BlockNode maxHeightBlockNode = getMaxHeightBlockNode(this.genesisBlockNode);

        if(block.getPrevBlockHash()==null & this.genesisBlockNode!=null){
            System.out.println("There has been a genesis block!");
            return false;
        }
        boolean status = maxHeightBlockNode.addBlock(maxHeightBlockNode, block, entryEOAs, removeEOAs, entryP_accounts, removeP_accounts);
        return status;
    }

    public void printEvent(Event event){
        System.out.println("-----------------------Start of An Event------------------------");
        System.out.println("Personal Social ID: "+event.getSocial_id_key());
        System.out.println("Description of Event: "+event.getEventMsg());
        System.out.println("Tag of Event: "+event.getTag());
        System.out.println("Belong to Series? "+event.getSeries_of_event());
        System.out.println("Publisher: "+event.getPublisher().hashCode());
        System.out.println("Receipt of Event: "+event.getApproval());
        System.out.println("------------------------End of An Event-------------------------");
    }

    public void printEOA(ExternalOwnedUser eoa){
        System.out.println("-----------------------Start of An External Owned User-----------------");
        System.out.println("EOA Public Key: "+eoa.get_id_key().hashCode());
        System.out.println("Balance: "+eoa.get_balance());
        System.out.println("Authority Level: "+eoa.get_auth());
        System.out.println("Maximum Constant Working Limit: "+eoa.get_coolDownTime());
        System.out.println("#Processed Cases: "+eoa.get_nonce());
        System.out.println("Can Action? "+eoa.get_canAction());
        System.out.println("-----------------------End of An External Owned User-------------------");
    }

    public void printPersonalLog(PersonalFileAccount p_account){
        System.out.println("Personal Log Start:");
        for(String tag:p_account.getPersonalLog().keySet()){
            System.out.println("Tag of log: "+tag);
            for(int i=0;i<p_account.getPersonalLog().get(tag).get_length();i++){
                System.out.println(p_account.getPersonalLog().get(tag).get_element(i));
            }
            System.out.println("Tag of log" + tag+" ==End!");
        }
        System.out.println("Personal Log End!");
    }

    public void printPFA(PersonalFileAccount p_account){
        System.out.println("-----------------------Start of A Personal File Account ----------------");
        System.out.println("Personal Social ID:" + p_account.getSocial_id_key());
        printPersonalLog(p_account);
        System.out.println("Record of Event Series: ");
        for(byte[] b: p_account.getUtxoPool().keySet()){
            System.out.println("series of event element: "+ Arrays.toString(b));
        }
        System.out.println("----------------------End of A Personal File Account ----------------------");
    }

    // print block content
    public void printLatestBlockContent(){
        BlockNode maxHeightBlockNode = getMaxHeightBlockNode(this.genesisBlockNode);
        System.out.println("# EOAs: "+maxHeightBlockNode.getEoa_accounts().size());
        for(ExternalOwnedUser eoa:maxHeightBlockNode.getEoa_accounts()){
            printEOA(eoa);
        }
        System.out.println("# PFAs: "+maxHeightBlockNode.getP_accounts().size());
        for(PersonalFileAccount pfa: maxHeightBlockNode.getP_accounts()){
            printPFA(pfa);
        }
        for(Event event: maxHeightBlockNode.getBlock().getEvents()){
            printEvent(event);
        }
    }
}
