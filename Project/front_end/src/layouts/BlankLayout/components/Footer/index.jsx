import React from 'react';
import { Grid } from '@alifd/next';
import styles from './index.module.scss';

const { Row, Col } = Grid;

export default function Footer() {
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <Row>
          <Col l="8">
            <h3 className={styles.title}>About Us</h3>
            <div className={styles.nav}>
              <a className={styles.link}>Privacy Policy</a>
              <a className={styles.link}>Join Us</a>
            </div>
          </Col>
          <Col l="8">
            <h3 className={styles.title}>Get Help</h3>
            <div className={styles.nav}>
              <a className={styles.link}>Contact Us</a>
              {/* <a className={styles.link}>使用文档</a> */}
            </div>
          </Col>
          <Col l="8">
            <h3 className={styles.title}>Development</h3>
            <div className={styles.nav}>
              <a className={styles.link}>Github Page</a>
            </div>
            {/* <img
              src="https://ice.alicdn.com/assets/images/qrcode.png"
              alt="qr-code"
              className={styles.qrcode}
            /> */}
          </Col>
        </Row>
        <p className={styles.copyRight}>Only for PHBS FS2019 Module1 Block Chain And Cryptocurrency!</p>
      </div>
    </div>
  );
}
