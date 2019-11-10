import React, { Component } from 'react';
import EmptyContent from './components/EmptyContent';
import CreateActivityForm from './components/CreateActivityForm';

export default function() {
  return (
    <div className="EoaSuccess-page">
      <EmptyContent />
      {/* 创建活动的表单 */}
      <CreateActivityForm />
    </div>
  );
}
