import React from 'react';
import { Grid } from '@alifd/next';
import styles from './index.module.scss';

const { Row, Col } = Grid;

const dataSource = [
  {
    title: 'At most one-stop',
    pic: require('./images/img1.png'),
    desc: 'One-stop solution for civilian, all affairs, only in one click',
  },
  {
    title: 'Validation Scheme',
    pic: require('./images/img3.png'),
    desc: 'Combine technology and manual validation',
  },
  {
    title: 'Block Chain Based',
    pic: require('./images/img4.png'),
    desc: 'Immutable  and can be verified by all nodes',
  },
  {
    title: 'Event Based',
    pic: require('./images/img2.png'),
    desc: 'One event = one external owned user + one personal file',
  },
];

export default function Feature() {
  return (
    <div className={styles.container}>
      <Row wrap className={styles.content}>
        {dataSource.map((item, index) => {
          return (
            <Col xxs="12" s="6" l="6" key={index} className={styles.item}>
              <img src={item.pic} className={styles.pic} alt="" />
              <h3 className={styles.title}>{item.title}</h3>
              <p className={styles.desc}>{item.desc}</p>
            </Col>
          );
        })}
      </Row>
    </div>
  );
}
