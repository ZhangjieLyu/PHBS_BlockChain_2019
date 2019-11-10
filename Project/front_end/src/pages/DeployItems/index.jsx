import React, { Component } from 'react';
import FailureDetail from './components/FailureDetail';
import SuccessDialog from './components/SuccessDialog';
import CreateActivityForm from './components/CreateActivityForm';
import EntryCard from './components/EntryCard';

export default function() {
  return (
    <div className="DeployItems-page">
      {/* 入口列表卡片 */}
      <EntryCard />
      {/* 提交失败详情展示 */}
      {/* <FailureDetail /> */}
      {/* 提示框-成功 */}
      <SuccessDialog />
      {/* 创建活动的表单 */}
      <CreateActivityForm />
    </div>
  );
}
