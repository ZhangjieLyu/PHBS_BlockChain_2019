import React, { Component } from 'react';
import EntryCard from './components/EntryCard';
import CreateActivityForm from './components/CreateActivityForm';
import SuccessDialog from './components/SuccessDialog';

export default function() {
  return (
    <div className="DeployEoa-page">
      {/* 入口列表卡片 */}
      <EntryCard />
      {/* 创建活动的表单 */}
      <CreateActivityForm />
      {/* 提示框-成功 */}
      {/* <SuccessDialog /> */}
    </div>
  );
}
