import React from 'react';
import IceContainer from '@icedesign/container';
import { Grid, Input, Table, Icon } from '@alifd/next';
import styles from './index.module.scss';

const { Row, Col } = Grid;

const mockData = {
  users: [
    {
      user_name: 'miner0',
      pub_hashcode:'1190362',
      balance: '0.0',
      auth: 'miner',
      processedCases: '0',
      maxWork: '10',
      canAction:'true',
    },
    {
      user_name: 'miner1',
      pub_hashcode:'1285008',
      balance: '10.0',
      auth: 'miner',
      processedCases: '1',
      maxWork: '10',
      canAction:'true',
    },
    {
      user_name: 'common_user0',
      pub_hashcode:'1250413',
      balance: '1.0',
      auth: 'common_user',
      processedCases: '1',
      maxWork: '0',
      canAction:'true',
    },
    {
      user_name: 'common_user1',
      pub_hashcode:'1174378',
      balance: '1.0',
      auth: 'common_user',
      processedCases: '1',
      maxWork: '0',
      canAction:'true',
    },
  ],
  pfa: [
    {
      socialID: 'T8Nye',
      utxoPool: 'empty',
      nonce: '1111',
    },
    {
      socialID: 'l43ku',
      utxoPool: 'empty',
      nonce: '1111',
    },
    {
      socialID: 'nQ9IP',
      utxoPool: 'empty',
      nonce: '1111',
    },

  ],
};

const accessColors = {
  miner: '#343A40',
  common_user: '#ABABAB',
  validator: '#83B451',
};

const stateColors = {
  empty: '#fdcb6e',
  notEmpty: '#ff7675',
};

export default function Index() {
  const handleSearch = (value) => {
    console.log(value);
  };

  const renderUserInfo = (value, index, record) => {
    return (
      <div className={styles.userInfo}>
        <h6 className={styles.userName}>{record.user_name}</h6>
        <p className={styles.userEmail}>HashCode:{record.pub_hashcode}</p>
      </div>
    );
  }

  const renderOthers = (value, index, record) => {
    return (
      <div className={styles.userInfo}>
        <h6 className={styles.userName}>Cases:{record.processedCases}</h6>
        <p className={styles.userEmail}>Limit:{record.maxWork}</p>
        <p className={styles.userEmail}>Status:{record.canAction}</p>
      </div>
    );
  };

  const renderAuth = (value, index, record) => {
    return(
      <div className={styles.userInfo}>
      <h6 className={styles.userName}>{record.auth}</h6>
      </div>
    )
  }

  const renderBal = (value, index, record) => {
    return(
      <div className={styles.userInfo}>
      <h6 className={styles.userName}>{record.balance}</h6>
      </div>
    )
  }

  const renderSocialID = (value, index, record) => {
    return(
      <div className={styles.userInfo}>
      <h6 className={styles.userName}>{record.socialID}</h6>
      </div>
    )
  }

  const renderSeriesRecord = (value, index, record) => {
    return(
      <div className={styles.userInfo}>
      <h6 className={styles.userName}>{record.utxoPool}</h6>
      </div>
    )
  }

  const renderNonce = (value, index, record) => {
    return(
      <div className={styles.userInfo}>
      <h6 className={styles.userName}>{record.nonce}</h6>
      </div>
    )
  }

  const renderAccess = (value) => {
    return (
      <span className={styles.userAccess} style={{background: accessColors[value] }}>
        {value}
      </span>
    );
  };

  const renderOper = () => {
    // window.location.href = "/#/getInfo/T8Nye"
    return <Icon type="edit" className={styles.editIcon} />;
  };

  const renderState = (value) => {
    return (
      <span className={styles.purchasesState} style={{color: stateColors[value] }}>
        {value}
      </span>
    );
  };

  return (
    <div >
      <Row wrap gutter="20">
        <Col xxx="24" s="12">
          <IceContainer className={styles.containerPadding}>
            <h2 className={styles.title}>External Owned User</h2>
            <div className={styles.searchInputCol}>
              <Input
                className={styles.searchInput}
                placeholder="Search Users ..."
                hasClear
                onChange={handleSearch}
                size="large"
              />
            </div>
            <Table dataSource={mockData.users} hasBorder={false}>
              <Table.Column
                title="User"
                dataIndex="avatar"
                cell={renderUserInfo}
              />
              <Table.Column
                title="Balance"
                dataIndex="name"
                cell={renderBal}
              />
              <Table.Column
                title="Access"
                dataIndex="access"
                cell={renderAuth}
              />
              <Table.Column
                title="Others"
                dataIndex="others"
                cell={renderOthers}
              />
              {/* <Table.Column title="" cell={renderOper} /> */}
            </Table>
          </IceContainer>
        </Col>
        <Col xxx="24" s="12">
          <IceContainer className={styles.containerPadding}>
            <h2 className={styles.title}>Personal File Account</h2>
            <div className={styles.searchInputCol}>
              <Input
                className={styles.searchInput}
                placeholder="Search Personal File Account ..."
                hasClear
                onChange={handleSearch}
                size="large"
              />
            </div>
            <Table dataSource={mockData.pfa} hasBorder={false}>
              <Table.Column title="Social ID" dataIndex="product" cell={renderSocialID}/>
              {/* <Table.Column title="" dataIndex="date" /> */}
              <Table.Column
                title="Series Record"
                dataIndex="state"
                cell={renderSeriesRecord}
              />
              <Table.Column title="Nonce" dataIndex="price" cell={renderNonce}/>
              <Table.Column title="Personl Log" cell={renderOper} />
            </Table>
          </IceContainer>
        </Col>
      </Row>
    </div>
  );
}

