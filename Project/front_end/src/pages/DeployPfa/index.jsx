import React, { Component } from 'react';
import EntryCard from './components/EntryCard';
import CreateActivityForm from './components/CreateActivityForm';

export default function() {
  return (
    <div className="DeployPfa-page">
      {/* 入口列表卡片 */}
      <EntryCard />
      {/* 创建活动的表单 */}
      <CreateActivityForm />
    </div>
  );
}
