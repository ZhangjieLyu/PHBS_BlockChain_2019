import React from 'react';
import IceContainer from '@icedesign/container';
import styles from './index.module.scss';

export default function DetailTable() {
  return (
    <div className="detail-table">
      <IceContainer title="Latest Block in Block Chain: Event">
        <ul style={styles.detailTable}>
          <li className={styles.detailItem}>
            <div className={styles.detailTitle}>Social ID：</div>
            <div className={styles.detailBody}>T8Nye</div>
          </li>
          <li className={styles.detailItem}>
            <div className={styles.detailTitle}>Document：</div>
            <div className={styles.detailBody}>get Ph.D. from THU</div>
          </li>
          <li className={styles.detailItem}>
            <div className={styles.detailTitle}>Tag：</div>
            <div className={styles.detailBody}>education</div>
          </li>
          <li className={styles.detailItem}>
            <div className={styles.detailTitle}>Series of Event：</div>
            <div className={styles.detailBody}>false</div>
          </li>
          <li className={styles.detailItem}>
            <div className={styles.detailTitle}>Receipt：</div>
            <div className={styles.detailBody}>
              <span className={styles.statusProcessing}>approve</span>
            </div>
          </li>
          <li className={styles.detailItem}>
            <div className={styles.detailTitle}>Publisher：</div>
            <div className={styles.detailBody}>
            1250413
            </div>
          </li>
        </ul>
      </IceContainer>
    </div>
  );
}
