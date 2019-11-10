import React from 'react';
import styles from './index.module.scss';
import { Link } from 'react-router-dom';

export default function Banner() {
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.title}>p-Chain, A Personal File Chain</div>
        <div className={styles.desc}>Make Governance Simple & Easy</div>
        {/* <a className={styles.link}>Log In</a> */}
        <Link to="/Login" className={styles.link}>Log In</Link>
      </div>
    </div>
  );
}
