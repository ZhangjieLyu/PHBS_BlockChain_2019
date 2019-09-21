

# Scrooge Coin

Scrooge Coin is a ledger held by every user. The coin itself is recorded by transaction, there're 2 types of transaction: one is *CreateCoins*, the other is *PayCoins*. 

## Intro. to Test case

The test cases are included in a file named *TestMain.java*. You can run each test case through clicking the triangle sign near *@Test*.

**FYI**, the number of accepted transactions are also printed.

### Test cases

1. A Valid Transaction:

   **Case**:

   ```R
   tx0: Scrooge --> Scrooge 25coins [Create Coins]
   tx1: Scrooge --> Alice   4coins  [Pay seperately]
   	 Scrooge --> Alice   5coins
        Scrooge --> Bob     6coins
   tx2: Alice --> Alice     2coins  [Divide Coins]
        Alice --> Alice     2coins
   tx3: Alice --> Bob       2coins  [Pay jointly]
        Alice --> Bob       5coins
   ```

   **Return**:

   tx1, tx2, tx3 are valid(*true*); return their transaction hash code.

   **Function Name**: *testValidTransaction*



2. Double-spending:

   **Case**:

   ```R
   tx0: Scrooge --> Scrooge 25coins [Create Coins]
   tx1: Scrooge --> Alice   20coins [Pay Coins]
   tx2: Scrooge --> Bob     20coins [*Double-spending*]
   ```

   **Return**:

   tx1(*true*), tx2(*false*); return their transaction hash code.

   **Function Name**: *testDoubleSpent*



3. Transaction not in UTXO:

   **Case**:

   ```R
   tx0: Scrooge --> Scrooge 25coins [Create Coins]
   tx1: Scrooge --> Alice   20coins [Pay Coins *NOT added to UTXO pool* due to accidents]
   tx2: Alice --> Bob       15coins [Pay Coins *Previous Tx NOT in UTXO pool*]       
   ```

   **Return**:

   tx1(*true*),tx2(*false*);return their transaction hash code.

   **Function Name**: *testTxNotInUTXOPool*



4. Invalid signature

   **Case**:

   ```R
   tx0: Scrooge --> Scrooge 25coins [Create Coins]
   tx1: Scrooge --> Alice   20coins [Pay Coins]
   tx2: Alice --> Bob       20coins [*Signed by Bob*]
   ```

   **Return**：

   tx1(*true*),tx2(*false*); return their transaction hash code.

   **Function Name**: testInvalidSig



5. Invalid  output number

   **Case**:

   ```R
   tx0: Scrooge --> Scrooge 25coins [Create Coins]
   tx1: Scrooge --> Alice   20coins [Pay Coins]
   tx2: Alice --> Bob       -5coins [*Invalid output number*]
   tx3: Alice --> Bob       90coins [*Invalid output number*]
   ```

   **Return**:

   tx1(*true*);tx2(*false*);tx3(*false*);return their transaction hash code.

   **Function Name**: testInvalidOutputCoins