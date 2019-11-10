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
              Congratulations!
            </h3>
            <p className={styles.description}>
              You have proposed a new event, <br/> 
              It will be added to the next block if it is valid.<br/>
              Added to block not guarantee successful proposal.
            </p>
          </div>
        </div>
      </IceContainer>
    </div>
  );
}
