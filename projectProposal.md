# Project Proposal

@author: Jiaxi Ren(1901212628@pku.edu.cn)

@author: Zhangjie Lyu(zjlv_math@pku.edu.cn)

## Motivation

**The problems to be solved?**

**Demands driven**

+ Chinese agriculture is not in the ranks of modernized agriculture yet. The outputs of agriculture product can’t match the acquirement and requirement of agriculture distributor. (gap between supply and demand)
+ With the rapid growth of comprehensive national strength and economics, consumer’s demand of food present a trend of diversification. Consumers are lack of trust to domestic agriculture product, the tradition certification of agriculture product needs to be reformed.



The motivation to this project is to provide a public chain to connect raw material producer and retailer in agricultural circulation (start-point to end-point connection). The expected functionality of this project is to realize identiﬁcation, veriﬁcation and tracing of agricultural circulation–from raw materials to end-point product. Also, it should oﬀer an incentive system to strengthen direct links among players in this circulation.



**Problems to solve**

+ Customized demands of high-value consumer; 
+  Consumers lack conﬁdence in domestic agricultural product; 
+  Connect farmers/farms and end-point retailer 
+  decrease asymmetric information cost.



**Policy driven**

The Blockchain concept is in accord with the government’s idea of cultivating modernized farmer.



**Difference from existing method**

In *Ant Finance*, block chain is used to identify,track,verify high-value product, including imported infant’s milk powder, Maotai, cosmetics, selected rice product. Mainly deployed on cross-border commodity, consumers can use Alipay/Taobao to see the route the product travelled by.(Tab.1) 

| Date  | Application                                                  | Features                                                     |
| ----- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Feb.  | Corporate   with Xiong’An new area, renting block chain application | Verify  authentication of renting information, renter’s and landlord’s  identification. Tamper-resistant. |
| Jun.  | Cross-border   transfer block chain, in Hongkong and Phil.   | Fast,   low intermediate (wireless transfer) fee, trackable, information security |
| Aug.  | Block  chain medical notes                                   | Tamper-resistant,   avoid risk of double-spending medical notes |
| Sept. | Rice origins tracing block chain                             | Solve the problem of tracing origins                         |
| Nov.  | Electronic  documents signing platform                       | Traceable,  tamper-resistant, shared by users, safer         |

Tab.1. Block Chain Scenario of *Ant Finance* in   2018



Compared to existing product, this product focus on product that needs veriﬁcation of origins but not of such high value, including broccoli, spinach, carrot, pork ribs etc. Besides, this project is trying to involve more players into the public chain other than alliance chain between limited-amount of players.

 The expected cooperator of this project is the government, since this project can be used to help connect farmers and market more tightly and increase the competence of domestic agricultural product in the market.



## Solution

**How to solve?**

### General ideas

Our solutions contain 2 parts. The first part is to solve the deployment in real world, the second part is to deal with the deployment in virtual world, i.e. the public chain.

*Part I. Deployment in real world*

The key problem of this part is to solve the origin verification. i.e. “hash a real-world object”. The solution depends on specific machines (analogue to POS of Visa card) instead of block chain. Details can be found in project report.

*Part II. Public Chain*

The public chain is the core of this ecosystem, it plays 4 key roles:

1. Make sure the information is immutable and trackable

2. Provide incentive to different players in the agricultural supply chain

3. Establish a feedback mechanism between any 2 players and broadcast it to the chain

4. Increase additional value of product if needed

Overall, the solution learns a lot from the design of Ethereum. Though the whole network cannot be accomplished till the submission deadline of the project, a demo will be displayed.

### Potential technical architecture

**Structure:** Mainly an Ethereum-based structural

**Difficulties:** 

1. Online technique can’t solve offline problems, offline problems should be solve individually.

2. How to decide incentive system to encourage farmer/merchants/retails join in the public chain, and the incentive system run in the public chain.

3. Profit problem, we can offer high quality services to acquire services fee, we can also issue altcoin to earn revenue (pre-publish).

4. Continues the profit problem. Our altcoin will be regulated by government and even need government’s endorsement. How to get rid of the venture of value investment of our altcoins and remain the incentive system when we declare our altcoin as digital currency?

5. The technique challenges of blockchain are throughput, time latency, capacity, bandwidth and security problems. As agriculture is an industry with periodic life time. (hardware restriction)

6. Blockchain can secure the characteristic of irrevocable but not authentic. The tradition solution is third-party notarization or government endorsement. While blockchain increase the cost of fraud because of its irretrievability. Thus reduce the probability of fraud in a long period.

 

**Project solution of offline fraud problem** Modeled encapsulation, heat seal, internal embed of QR code (prevent tampering)

**Altcoins role** reputation or currency? If altcoins are not currency, difficult to reach consensus. 

**Mining** proof-of-stake，block size dynamic adjustment, average time creating a block?

**Message process** hundreds to one thousand per second (nowadays blockchain limit:3000/per second)

**Smart contract:** payable function, delay payment.

**Consensus system:** The principle is preventing generating fork in best effort. If fork appear: modified ghost consensus, uncle block transactions should be re-verified.

**Transaction broadcast/Blockchain information broadcast** flooding

**Nodes status** set super nodes or not?

**Public chain access mechanism** weak access mechanism, differentiate the access conditions of the public chain based on different business

**Upstream** supplier (farmer / agriculture corporation) 

**Midstream** merchants/wholesaler

**Downstream** retailers

**Incentive system** altcoins/government guide/security promise/price advantage/lower cost/ tell a good story!

**Cold start** The government take the lead in. If nodes numbers are limited in a Proof-of-stake system, rich nodes will obtain a disproportionate advantage.



## Rationale

**why such solution but not other alternative**

In this part, we would like to discuss the essential of introducing block chain other than any centralized business solution.
**Decrease cost of involvement** To participate in a public chain, the cost will be much lower than traditional negotiation, moreover, most farmers in China may not have such resource and ability do such negotiation.

**Comparative advantage** In economics, it is always recommended to exert comparative advantage whenever possible. Nowadays, most farmers do both producing and merchandising. This behavior cut oﬀ their potential (and rational) proﬁt.

**Transparent, trustless trust mechanism** The problems such as “fake goods”、“food safety” are the big problems that people have been studying but can't cure. Blockchain technology, with its characteristics of information openness, transparency, non-tamper ability and traceability, seems to bring new hope for commodity traceability.



**Why block chain other than distributed database?**

1. Blockchain naturally has consistency and traceability, tamper-evident log; distributed database doesn’t have such characteristic.

2. Blockchain consensus system philosophically decentralized. More adapt to build a supervision and notarization system; distributed database has a logical center, more adapt to pure information recording log. 

