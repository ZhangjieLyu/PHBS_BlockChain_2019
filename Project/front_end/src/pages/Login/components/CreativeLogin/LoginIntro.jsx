import React from 'react';
import styles from './index.module.scss';

export default function LoginIntro() {
  return (
    <div  className={styles.containerForget}>
      <div className={styles.logo}>
        <a href="#" className={styles.link}>
          <img
            className={styles.logoImg}
            src={require('./images/logo.png')}
            alt="logo"
          />
        </a>
      </div>
      <div className={styles.title}>
        At most one-stop <br />
        Technology contributes to society
      </div>
      <p className={styles.description}>Make governance simple & easy</p>
      <div className={styles.border} />
    </div>
  );
}
