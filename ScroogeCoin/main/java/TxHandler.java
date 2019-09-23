import java.util.*;

public class TxHandler {

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a defensive copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */

    // define a new object utxoPool
    private UTXOPool utxoPool;

    public TxHandler(UTXOPool utxoPool) {
        // IMPLEMENT THIS
        this.utxoPool = new UTXOPool(utxoPool);
    }

    /**
     * @return UTXO Pool:
     * Since A defensive copy cannot be visited outside the class
     * This method is used to get UTXO pool after TxHandling.
     */
    public UTXOPool getHandledUtxoPool(){
        return this.utxoPool;
    }


    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid,
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     * values; and false otherwise.
     */

    // this function is to go through one transaction.
    // one transaction is compromised of several inputs(ArrayList) and several outputs(ArrayList)
    public boolean isValidTx(Transaction tx) {
        // IMPLEMENT THIS
        int i = 0; //index
        double inputSum = 0; //coinValue input
        Set<UTXO> spentUtxo = new HashSet<UTXO>(); //spent transaction outputs, avoid repeats

        // for loop, to go through all inputs
        // don't care output's pubKey
        for (Transaction.Input input : tx.getInputs()) {
            UTXO utxo = new UTXO(input.prevTxHash, input.outputIndex);

            // verify the transaction has not been executed and
            // whether the transaction is in the utxo pool.
            if (spentUtxo.contains(utxo)) {
                return false;
            }

            spentUtxo.add(utxo);

            if (!utxoPool.contains(utxo)) {
                return false;
//                return true;
            }

            // verify the signature
            if (!Crypto.verifySignature(utxoPool.getTxOutput(utxo).address, tx.getRawDataToSign(i), input.signature)) {
                return false;
            }

            inputSum += utxoPool.getTxOutput(utxo).value;
            i++;
        }

        double outputSum = 0;
        // get sum of the total transaction output(check owner's amount of coins claimed by prevTx)
        for (Transaction.Output output : tx.getOutputs()) {
            if (output.value < 0) {
                return false;
            } else {
                outputSum += output.value;
            }
        }
        // reject the transaction if the owner try to transfer more coins than the coins he owns.
        if (outputSum > inputSum) {
            return false;
//            return true;
        }

        //if all the above situations not happen.
        return true;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS
        Set<Transaction> acceptedTx = new HashSet<Transaction>();
        Set<Transaction> invalidTx = new HashSet<Transaction>();
        Set<Transaction> acceptedTx_copy = new HashSet<Transaction>();

        //go through the array of possible Txs.
        for (Transaction tx : possibleTxs) {
            if (isValidTx(tx)) {
                DealValidTx(acceptedTx, acceptedTx_copy, tx);
            } else {
                invalidTx.add(tx);
            }
        }
//        System.out.println("4.UTXO pool size: "+utxoPool.getAllUTXO().size());
        while(!acceptedTx_copy.isEmpty()) {
            acceptedTx_copy.clear();
            for(Transaction invalid_tx: invalidTx) {
                if(isValidTx(invalid_tx)) {
                    DealValidTx(acceptedTx, acceptedTx_copy, invalid_tx);
                }
            }
            invalidTx.removeAll(acceptedTx_copy);
        }
//        System.out.println("5.UTXO pool size: "+utxoPool.getAllUTXO().size());

        // fix the size of transactions
//        System.out.println("tx size is:" +acceptedTx.toArray(new Transaction[acceptedTx.size()]).length);
        Transaction[] Arr_acceptedTx = acceptedTx.toArray(new Transaction[acceptedTx.size()]);
//        System.out.println("6.UTXO pool size: " + utxoPool.getAllUTXO().size());

        return Arr_acceptedTx;
    }

        public void DealValidTx(Set<Transaction> acceptedTx, Set<Transaction> acceptedTx_copy,Transaction tx){
            for (Transaction.Input oneDeal : tx.getInputs()) {
                // check inputs in transaction, remove them from UTXOPool
                UTXO possibleDeal = new UTXO(oneDeal.prevTxHash, oneDeal.outputIndex);
//                System.out.println("1.UTXO pool size: "+utxoPool.getAllUTXO().size());
                if (utxoPool.contains(possibleDeal)) {
//                    System.out.println("drop a utxo");
                    utxoPool.removeUTXO(possibleDeal);
//                    System.out.println("2.UTXO pool size: "+utxoPool.getAllUTXO().size());
                }
            }

            // outputs are new future inputs, added to UTXOPool
            List<Transaction.Output> outputs = tx.getOutputs();
            for (int i = 0; i < outputs.size(); i++) {
                UTXO utxo = new UTXO(tx.getHash(), i);
                utxoPool.addUTXO(utxo, outputs.get(i));
//                System.out.println("3.UTXO pool size: "+utxoPool.getAllUTXO().size());
            }
            acceptedTx_copy.add(tx);
            acceptedTx.add(tx);

        }
}


