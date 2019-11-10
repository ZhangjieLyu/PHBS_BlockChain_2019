import React, { useState } from 'react';
import IceContainer from '@icedesign/container';
import {
  Input,
  Checkbox,
  Select,
  DatePicker,
  Switch,
  Radio,
  Form,
} from '@alifd/next';
import styles from './index.module.scss';


// FormBinder 用于获取表单组件的数据，通过标准受控 API value 和 onChange 来双向操作数据
const CheckboxGroup = Checkbox.Group;
const RadioGroup = Radio.Group;
const { RangePicker } = DatePicker;
const FormItem = Form.Item;


const formItemLayout = {
  labelCol: { xxs: "6", s: "2", l: "2" },
  wrapperCol: { s: "12", l: "10" },
};

export default function Index() {
  const [value, setValue] = useState({
    name: '',
    tag: 'tag1',
    approval: 'status3',
  });

  const onFormChange = (value) => {
    setValue(value);
  };

  const reset = () => {

  };

  const submit = (value, error) => {
    console.log('error', error, 'value', value);
    window.location.href = '/#/proposeEventSuccess'
    if (error) {
      // 处理表单报错
    }
    // 提交当前填写的数据
  };

  return (
    <div className="create-activity-form">
      <IceContainer title="Porpose Event" className={styles.container}>
        <Form
          value={value}
          onChange={onFormChange}
        >
            <FormItem {...formItemLayout} label="Social ID："
              required
              requiredMessage="Social ID cannot be empty!"
            >
              <Input name="name" className={styles.inputWidth} />
            </FormItem>

            <FormItem {...formItemLayout} label="Document："
              required
              requiredMessage="Message cannot be empty!"
            >
              <Input name="eventMessage" className={styles.inputWidth} />
            </FormItem>

            <FormItem {...formItemLayout} label="Tag：">
              <Select
                name="tag"
                dataSource={[
                  { label: 'education', value: 'tag1' },
                  { label: 'household', value: 'tag2' },
                ]}
              />
            </FormItem>

            <FormItem {...formItemLayout} label="Seris of Event：">
              <Switch name="series_of_event" />
            </FormItem>
            
            <FormItem {...formItemLayout} label="Ref Event："
            >
              <Input name="prevEventHash" className={styles.inputWidth} />
            </FormItem>
            
            <FormItem {...formItemLayout} label="Approve?：">
              <Select
                name="approval"
                dataSource={[
                  { label: 'approve', value: 'status1' },
                  { label: 'reject', value: 'status2' },
                  { label: 'pending', value: 'status3'},
                ]}
              />
            </FormItem>

            <FormItem {...formItemLayout} label=" ">
              <Form.Submit type="primary" validate onClick={submit}>
                Propose Now
                </Form.Submit>
              <Form.Reset className={styles.resetBtn} onClick={reset}>
                Reset
                </Form.Reset>
            </FormItem>
        </Form>
      </IceContainer>
    </div>
  );
}
