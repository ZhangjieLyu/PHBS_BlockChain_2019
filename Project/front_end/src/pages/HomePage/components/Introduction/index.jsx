import React from 'react';
import styles from './index.module.scss';

export default function Introduction() {
  return (
    <div className={styles.container}>
      <h3 className={styles.title}>Brief Introduction</h3>
      <p className={styles.desc}>
        A brand new era of governance system, make it simple & easy both for civilian and officers. 
      </p>
    </div>
  );
}
