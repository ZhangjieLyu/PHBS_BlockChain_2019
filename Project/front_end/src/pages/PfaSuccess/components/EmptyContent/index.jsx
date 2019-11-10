import React from 'react';
import IceContainer from '@icedesign/container';
import styles from './index.module.scss';

export default function EmptyContent() {
  return (
    <div className={styles.exceptionContent}>
      <IceContainer>
        <div className={styles.exceptionContent}>
          <img
            src={require('./images/TB1WNNxjBHH8KJjy0FbXXcqlpXa-780-780.png')}
            style={styles.image}
            className={styles.imgException}
            alt="empty"
          />
          <div style={styles.prompt}>
            <h3 className={styles.title}>
              New Personal File Account Initialized
            </h3>
            <p className={styles.description}>
              This file has been added to waiting list, <br/> 
              It will be in service in the next block if it is valid.
            </p>
          </div>
        </div>
      </IceContainer>
    </div>
  );
}
