import React, { Component } from 'react';
import CreativeLogin from './components/CreativeLogin';
import FullFooter from './components/FullFooter';

export default function() {
  return (
    <div className="Login-page">
      {/* 左右布局的登录页 */}
      <CreativeLogin />
      {/* 适用于介绍页的响应式页脚 */}
      {/* <FullFooter /> */}
    </div>
  );
}
