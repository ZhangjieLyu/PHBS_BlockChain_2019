import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BlockNode {
    /** expand block's attributes, make it convenient to connect previous and forwarding block(enclosed in block node)
     *  help maintain 2 pools(eventPool and personalFileAccountUTXOPool)
     *  major attributes:
     *  block: enclosed block information, inheriting from {@class block}
     *  height: record height of a block, counting from 1.
     *  parentNode: a block node, current node's parent node, unique;
     *  childNodes: an ArrayList<BlockNode>, not unique;
     *  PersonalFileAccount: an ArrayList<PersonalFileAccount>, maintaining status of personal file account
     *  ExternalOwnedAccount: an ArrayList<ExternalOwnedAccount>, maintaining status of external owned account
     *  EventPool: a pool, recoding existing events with their publisher(EOA) in current block
     *  PersonalFileAccountUTXOPool: a pool, recording existing events with their influenced personal file account in current block
     *  Await EOAs: these EOAs are in a waiting list to be added into EOA of the block;
     *  Await PersonalFileAccounts: these personal file accounts are in a waiting list to be added into Personal File Account of the block
     *  removeEOA: these EOAs are to be abandoned in the next block
     *  removePersonalFileAccount: these PersonalFileAccount are to be abandoned in the next block
     */

    public static final int CUT_OFF_HEIGHT = 1; // only an indicator, no actual usage, implies max number of uncle BlockNode to be included
    private static final double COINBASE = 10;
    private static final double FRACTION_NUMBER = 0.1;

    private Block block;
    private int height;
    private BlockNode parentNode;
    private ArrayList<BlockNode> childNodes;
    private ArrayList<PersonalFileAccount> p_accounts;
    private ArrayList<ExternalOwnedUser> eoa_accounts;
    private EventPool eventPool;
    private PersonalFileAccountUTXOPool p_pool;
    private ArrayList<ExternalOwnedUser> awaitEOAs;
    private ArrayList<PersonalFileAccount> awaitPersonalFileAccounts;
    private ArrayList<ExternalOwnedUser> removeEOA;
    private ArrayList<PersonalFileAccount> removePersonalFileAccount;
    private BlockNode uncleBlockNode;

    public BlockNode(Block block){
        this.block = block;
        this.height = 1;
        this.parentNode = null;
        this.childNodes = new ArrayList<BlockNode>();
        this.p_accounts = new ArrayList<PersonalFileAccount>();
        this.eoa_accounts = new ArrayList<ExternalOwnedUser>();
        this.eventPool = new EventPool();
        this.p_pool = new PersonalFileAccountUTXOPool();
        this.awaitEOAs = new ArrayList<ExternalOwnedUser>();
        this.awaitPersonalFileAccounts = new ArrayList<PersonalFileAccount>();
        this.removeEOA = new ArrayList<ExternalOwnedUser>();
        this.removePersonalFileAccount = new ArrayList<PersonalFileAccount>();
        this.uncleBlockNode = null;
    }

    private BlockNode(Block block, int height, BlockNode parentNode, ArrayList<BlockNode> childNodes,
                     ArrayList<PersonalFileAccount> p_accounts, ArrayList<ExternalOwnedUser> eoa_accounts,
                     EventPool eventPool, PersonalFileAccountUTXOPool p_pool, ArrayList<ExternalOwnedUser> awaitEOAs,
                     ArrayList<PersonalFileAccount> awaitPersonalFileAccounts, ArrayList<ExternalOwnedUser> removeEOA,
                     ArrayList<PersonalFileAccount> removePersonalFileAccount){
        this.block = block;
        this.height = height;
        this.parentNode = parentNode;
        this.childNodes = childNodes;
        this.p_accounts = p_accounts;
        this.eoa_accounts = eoa_accounts;
        this.eventPool = eventPool;
        this.p_pool = p_pool;
        this.awaitEOAs = awaitEOAs;
        this.awaitPersonalFileAccounts = awaitPersonalFileAccounts;
        this.removeEOA = removeEOA;
        this.removePersonalFileAccount = removePersonalFileAccount;
        this.uncleBlockNode = null;
    }

    // get attributes
    public Block getBlock(){
        return this.block;
    }

    public int getHeight(){
        return this.height;
    }

    public BlockNode getParentNode(){
        return this.parentNode;
    }

    public ArrayList<BlockNode> getChildNodes() {
        return childNodes;
    }

    public ArrayList<ExternalOwnedUser> getEoa_accounts() {
        return eoa_accounts;
    }

    public ArrayList<PersonalFileAccount> getP_accounts() {
        return p_accounts;
    }

    public EventPool getEventPool() {
        return eventPool;
    }

    public PersonalFileAccountUTXOPool getP_pool() {
        return p_pool;
    }

    public ArrayList<ExternalOwnedUser> getAwaitEOAs() {
        return awaitEOAs;
    }

    public ArrayList<ExternalOwnedUser> getRemoveEOA() {
        return removeEOA;
    }

    public ArrayList<PersonalFileAccount> getAwaitPersonalFileAccounts() {
        return awaitPersonalFileAccounts;
    }

    public ArrayList<PersonalFileAccount> getRemovePersonalFileAccount() {
        return removePersonalFileAccount;
    }

    //set attributes
    private void setParentNode(BlockNode parentNode) {
        this.parentNode = parentNode;
    }

    private void addChildNode(BlockNode childNodes) {
        this.childNodes.add(childNodes);
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public void setEoa_accounts(ArrayList<ExternalOwnedUser> eoa_accounts) {
        this.eoa_accounts = eoa_accounts;
    }

    public void setP_accounts(ArrayList<PersonalFileAccount> p_accounts) {
        this.p_accounts = p_accounts;
    }

    private void setUncleBlockNode(BlockNode blockNode){
        if(Arrays.equals(blockNode.getBlock().getPrevBlockHash(), parentNode.getBlock().getPrevBlockHash())){
            this.uncleBlockNode = blockNode;
        }else{
            System.out.println("Uncle block node must share the same previous block with parent block");
        }
    }

//    // get max height node
//    public BlockNode getMaxHeightBlockNode(BlockNode blockNode){
//        if(blockNode.childNodes.isEmpty()){
//            return blockNode;
//        }else{
//            int blockHeight = blockNode.getHeight();
//            for(BlockNode childBlockNode:blockNode.childNodes){
//                BlockNode tempNode = getMaxHeightBlockNode(childBlockNode);
//                if(tempNode.getHeight()>blockHeight){
//                    blockHeight = tempNode.getHeight();
//                    blockNode = tempNode;
//                }
//            }
//        }
//        return blockNode;
//    }

    // add block
    /**
     * while adding block, first, check the following facts:
     * 1. if the block's prevBlockHash equal to parent block's hash
     * 2. if all events in the new block are valid
     * 2.1. additionally, check whether personal account and EOA exist or not
     * 2.2. also, check potential removes or adds
     * 3. if the block's height > current max height - CUT_OFF_HEIGHT(not shown here, depend on genesis block node, checked in {@class BlockChain})
     * Second, if validation test passed, update newest blockNode, including:
     * 0. update parent node's child node
     * 1. update block's parentBlockNode
     * 2. update block's height
     * 3. update event pool
     * 4. update personal file account utxoPool
     * 5. according to above information, update EOA and personal file accounts
     * 6. update awaiting account list and remove account list
     */
    public boolean addBlock(BlockNode parentBlockNode, Block block,
                            ArrayList<ExternalOwnedUser> proposedAddEOAs, ArrayList<ExternalOwnedUser> proposedRemoveEOAs,
                            ArrayList<PersonalFileAccount> proposedAddPAccount, ArrayList<PersonalFileAccount> proposedRemovePAccount){
        // check potential block's validity
        // if error, should use the class ByteArrayWrapper
        if(!Arrays.equals(block.getPrevBlockHash(), parentBlockNode.getBlock().getHash())){
            System.out.println("Intended parent block should not be followed by proposed block!");
            return false;
        }

        if(block.getMiner() == null){
            System.out.println("Not a valid proposed block");
            return false;
        }

        // check if everyone who proposed an event is in parentBlockNode's EOA and every one's canAction status
        HashMap<PublicKey, ExternalOwnedUser> pre_Validation = new HashMap<PublicKey, ExternalOwnedUser>();
        for(ExternalOwnedUser eoa: parentBlockNode.getEoa_accounts()){
            pre_Validation.put(eoa.get_id_key(), eoa);
        }
        for(Event event: block.getEvents()){
            if(!pre_Validation.keySet().contains(event.getPublisher())){
                System.out.println("External Owned User "+event.getPublisher().hashCode()+" doesn't exist");
                return false;
            }
            if(!pre_Validation.get(event.getPublisher()).get_canAction()){
                System.out.println("This External User " +event.getPublisher().hashCode()+ " is not allowed to propose an event!");
                return false;
            }
        }

        EventHandler currentEventHandler = new EventHandler(parentBlockNode.getEventPool(), parentBlockNode.getP_pool());
        ArrayList<Event[]> currentHandledEvents = currentEventHandler.handleEvent(block.getEvents().toArray(new Event[block.getEvents().size()]));
        Event[] currentValidEvents = currentHandledEvents.get(0);
        Event[] currentInvalidEvents = currentHandledEvents.get(1);
        if(currentValidEvents.length!=block.getEvents().size()){
            System.out.println("Not all events collected are valid. There're "+currentInvalidEvents.length+" invalid events!");
            return false;
        }

        //update information
        block.finalize(); //block has been finalized!
        BlockNode blockNode = new BlockNode(block);
        parentBlockNode.addChildNode(blockNode);
        blockNode.setParentNode(parentNode);
        blockNode.setHeight(parentBlockNode.getHeight()+1);

        blockNode.eventPool = new EventPool();
        blockNode.p_pool = new PersonalFileAccountUTXOPool();

        //check whether block miner is in the EOA state
        if(!parentBlockNode.getEoa_accounts().contains(block.getMiner())){
            System.out.println("Miner is not in current EOAs!");
            return false;
        }

        // add EOAs and PFAs from parent's node
        ArrayList<ExternalOwnedUser> inherited_EOAs = parentBlockNode.getEoa_accounts();
        inherited_EOAs.addAll(parentBlockNode.getAwaitEOAs());
        inherited_EOAs.removeAll(parentBlockNode.getRemoveEOA());
        blockNode.eoa_accounts.addAll(inherited_EOAs);
        blockNode.eoa_accounts.addAll(parentBlockNode.awaitEOAs);
        blockNode.eoa_accounts.removeAll(parentBlockNode.removeEOA);

        ArrayList<PersonalFileAccount> inherited_p_accounts = parentBlockNode.getP_accounts();
        inherited_p_accounts.addAll(parentBlockNode.getAwaitPersonalFileAccounts());
        inherited_p_accounts.removeAll(parentBlockNode.getRemovePersonalFileAccount());
        blockNode.p_accounts.addAll(inherited_p_accounts);
        blockNode.p_accounts.addAll(parentBlockNode.awaitPersonalFileAccounts);
        blockNode.p_accounts.removeAll(parentBlockNode.removePersonalFileAccount);

        //create hash map for convenience of searching
        HashMap<PublicKey,ExternalOwnedUser> inherited_EOAs_maps = new HashMap<PublicKey,ExternalOwnedUser>();
        HashMap<String,PersonalFileAccount> inherited_p_accounts_maps = new HashMap<String, PersonalFileAccount>();
        for(ExternalOwnedUser eoa:inherited_EOAs){
            inherited_EOAs_maps.put(eoa.get_id_key(),eoa);
        }
        for(PersonalFileAccount p_account: inherited_p_accounts){
            inherited_p_accounts_maps.put(p_account.getSocial_id_key(),p_account);
        }

        // create valid awaiting list and remove list
        ArrayList<ExternalOwnedUser> validWaitingEOAs = new ArrayList<ExternalOwnedUser>();
        ArrayList<ExternalOwnedUser> validRemoveEOAs = new ArrayList<ExternalOwnedUser>();
        ArrayList<PersonalFileAccount> validWaitingPAccounts = new ArrayList<PersonalFileAccount>();
        ArrayList<PersonalFileAccount> validRemovePAccounts = new ArrayList<PersonalFileAccount>();
        for(ExternalOwnedUser eoa: proposedAddEOAs){
            if(!inherited_EOAs_maps.containsKey(eoa.get_id_key())){
                validWaitingEOAs.add(eoa);
            }
        }
        for(ExternalOwnedUser eoa: proposedRemoveEOAs){
            if(inherited_EOAs_maps.containsKey(eoa.get_id_key())){
                validRemoveEOAs.add(eoa);
            }
        }
        for(PersonalFileAccount p_account: proposedAddPAccount){
            if(!inherited_p_accounts_maps.containsKey(p_account.getSocial_id_key())){
                validWaitingPAccounts.add(p_account);
            }
        }
        for(PersonalFileAccount p_account: proposedRemovePAccount){
            if(inherited_p_accounts_maps.containsKey(p_account.getSocial_id_key())){
                validRemovePAccounts.add(p_account);
            }
        }
        blockNode.awaitEOAs = validWaitingEOAs;
        blockNode.awaitPersonalFileAccounts = validWaitingPAccounts;
        blockNode.removeEOA = validRemoveEOAs;
        blockNode.removePersonalFileAccount = validRemovePAccounts;

        /**update event pool and update corresponding EOA
         * meanwhile, update p_uPool and update corresponding personal file account
         * field to be updated:
         * In EOA: balance, coolDownTime
         * In PersonalFileAccount: personal log
         */
        // update miner's balance and update miner's status
        if(block.getMiner()!=null){
            block.getMiner().add_balance(COINBASE);
            block.getMiner().EOAHandler();
        }
        if(blockNode.uncleBlockNode!=null){
            blockNode.uncleBlockNode.getBlock().getMiner().add_balance(FRACTION_NUMBER*6*COINBASE);
            blockNode.uncleBlockNode.getBlock().getMiner().EOAHandler();
        }
        // update other information
        for(Event event:block.getEvents()){
            blockNode.eventPool.addEvent(event, inherited_EOAs_maps.get(event.getPublisher()));
            blockNode.p_pool.addElement(event, inherited_p_accounts_maps.get(event.getSocial_id_key()));

            inherited_EOAs_maps.get(event.getPublisher()).add_balance(FRACTION_NUMBER*COINBASE);
            inherited_EOAs_maps.get(event.getPublisher()).EOAHandler();

            inherited_p_accounts_maps.get(event.getSocial_id_key()).getPersonalLog().get(event.getTag()).push_log(event.getEventMsg().concat(event.getApproval()));

            // update series of event
            if(event.getSeries_of_event()){
                inherited_p_accounts_maps.get(event.getSocial_id_key()).getUtxoPool().remove(event.getPrevEventHash());
                inherited_p_accounts_maps.get(event.getSocial_id_key()).getUtxoPool().put(event.getHash(), event);
            }
        }
        return true;
    }
}
