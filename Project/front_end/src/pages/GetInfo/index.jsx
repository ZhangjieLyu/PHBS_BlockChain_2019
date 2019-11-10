import React, { Component } from 'react';
import ColumnsTable from './components/ColumnsTable';
import DetailTable from './components/DetailTable';
import DetailTable1 from './components/DetailTable1';
import EntryCard from './components/EntryCard';


      
export default function() {
  return (
    <div className="GetInfo-page">
      {/* 入口列表卡片 */}
      <EntryCard />
      {/* 两栏布局的表格 */}
      <ColumnsTable />
      {/* 展示详情信息的表格 */}
      <DetailTable />
      {/* 展示详情信息的表格 */}
      <DetailTable1 />
    </div>
  );
}
