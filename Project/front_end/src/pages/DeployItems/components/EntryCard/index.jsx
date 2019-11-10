import React from 'react';
import IceContainer from '@icedesign/container';
import styles from './index.module.scss';

const list = [
  {
    title: 'New EOA',
    img: '//gw.alicdn.com/tfscom/TB1OyT.RVXXXXcpXXXXXXXXXXXX.png',
    url: '/#/deployUser/deployEOA',
  },
  {
    title: 'New PFA',
    img: '//img.alicdn.com/tfs/TB1g6cGRFXXXXa9XXXXXXXXXXXX-140-140.png',
    url: '/#/deployUser/deployPFA',
  },
  {
    title: 'New Events & Blocks',
    img: '//img.alicdn.com/tfs/TB1hJ7dRFXXXXcgXFXXXXXXXXXX-140-140.png',
    url: '/#/deployBlock/deployItems',
  },
  {
    title: 'Find the Latest Info.',
    img: '//img.alicdn.com/tfs/TB196v1RFXXXXb6aXXXXXXXXXXX-140-140.png',
    url: '/#/getInfo',
  },
];

export default function EntryCard() {
  return (
    <IceContainer
      className={styles.nl}
    >
      {list.map((item, index) => {
        return (
          <div key={index} className={styles.item}>
            <a href={item.url} className={styles.link}>
              <img src={item.img} className={styles.cover} alt={item.title} />
              <div className={styles.title}>{item.title}</div>
            </a>
          </div>
        );
      })}
    </IceContainer>
  );
}
