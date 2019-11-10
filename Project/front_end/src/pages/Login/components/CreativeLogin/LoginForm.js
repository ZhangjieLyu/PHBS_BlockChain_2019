import React from 'react';
import { Message } from '@alifd/next';
import AuthForm from './AuthForm';
import { Link } from 'react-router-dom';

export default function LoginForm() {
  const formChange = (value) => {
    console.log('formChange:', value);
  };

  const handleSubmit = (errors, values) => {
    if (errors) {
      console.log('errors', errors);
      return;
    }
    console.log('values:', values);
    Message.success('Log in success');
    // 登录成功后做对应的逻辑处理
    window.location.href = '/#/deployUser/deployEoa'
    // <Link to="/deployUser/deployEoa" className={styles.link}></Link>
  };

  const config = [
    {
      label: 'User ID',
      component: 'Input',
      componentProps: {
        placeholder: 'User ID',
        size: 'large',
        maxLength: 20,
      },
      formBinderProps: {
        name: 'name',
        required: true,
        message: 'Can not be empty',
      },
    },
    {
      label: 'Password',
      component: 'Input',
      componentProps: {
        placeholder: 'Password',
        htmlType: 'passwd',
      },
      formBinderProps: {
        name: 'passwd',
        required: true,
        message: 'Can not be empty',
      },
    },
    {
      label: 'Remembe Me',
      component: 'Checkbox',
      componentProps: {},
      formBinderProps: {
        name: 'checkbox',
      },
    },
    {
      label: 'Log In',
      component: 'Button',
      componentProps: {
        type: 'primary',
      },
      formBinderProps: {},
    },
  ];

  const initFields = {
    name: '',
    passwd: '',
    checkbox: false,
  };

  const links = [
    { to: '/register', text: 'Sign Up' },
    { to: '/forgetpassword', text: 'Forget Password' },
  ];

  return (
    <AuthForm
      title="Log In"
      config={config}
      initFields={initFields}
      formChange={formChange}
      handleSubmit={handleSubmit}
      links={links}
    />
  );
}
