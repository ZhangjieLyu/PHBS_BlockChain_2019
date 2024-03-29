import React, { useState, useEffect } from 'react';
import IceContainer from '@icedesign/container';
import { Dialog, Button, Icon } from '@alifd/next';
import { enquireScreen } from 'enquire-js';
import styles from './index.module.scss';

export default function Index(props) {
  const [visible, setVisible] = useState(false);
  const [isMobile, setMobile] = useState(false);

  const enquireScreenRegister = () => {
    const mediaCondition = 'only screen and (max-width: 720px)';

    enquireScreen((mobile) => {
      setMobile(mobile);
    }, mediaCondition);
  };

  useEffect(() => {
    enquireScreenRegister();
  }, []);

  const showDialog = () => {
    setVisible(true);
  };
  const hideDialog = () => {
    window.location.href = "/#/getInfo"
    setVisible(false);
  };

  const renderFooter = () => {
    return (
      <div className={styles.footer}>
        {/* <Button onClick={hideDialog}>稍后前往</Button> */}
        <Button onClick={hideDialog} type="primary">
          Go Back
        </Button>
      </div>
    );
  };

  const dialogStyle = {
    width: isMobile ? 'auto' : '640px',
    height: isMobile ? 'auto' : '340px',
  };

  return (
    <IceContainer>
      <Dialog
        className="complex-dialog"
        style={{ ...dialogStyle }}
        autoFocus={false}
        footer={renderFooter()}
        title="Personal Log"
        isFullScreen
        onClose={hideDialog}
        {...props}
        visible={visible}
      >
        <div className={styles.dialogContent}>
          <img
            className={styles.icon}
            src="//img.alicdn.com/tfs/TB1GOHLXyqAXuNjy1XdXXaYcVXa-52-52.png"
            srcSet="//img.alicdn.com/tfs/TB1h_K_b4rI8KJjy0FpXXb5hVXa-104-104.png"
            alt=""
          />
          {/* <div className={styles.info}>
            恭喜您成功创作平台<br />现在可以认证符合自己的角色啦
          </div> */}
          <div className={styles.extraInfo}>
          Personal Log Start:<br/>
          Tag of log: education<br/>
          get MA from PKUapprove<br/>
          get Ph.D. from THUapprove<br/>
          Tag of logeducation ==End!<br/>
          Tag of log: household<br/>
          Tag of loghousehold ==End!<br/>
          Personal Log End!<br/>
          </div>
          {/* <div className={styles.authList}>
            <div className={styles.authItem}>
              <Icon className={styles.authItemIcon} size="xs" type="select" />
              V 标头像
            </div>
            <div className={styles.authItem}>
              <Icon className={styles.authItemIcon} size="xs" type="select" />
              角色标志
            </div>
            <div className={styles.authItem}>
              <Icon className={styles.authItemIcon} size="xs" type="select" />
              优先发表
            </div> */}
          {/* </div> */}
        </div>
      </Dialog>

      <Button type="primary" onClick={showDialog}>
        Dialog
      </Button>
    </IceContainer>
  );
}
