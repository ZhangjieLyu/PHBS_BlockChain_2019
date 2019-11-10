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
import { Link } from 'react-router-dom';


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
    name_sk: '',
    name_pk: '',
    area: 'location1',
  });

  const onFormChange = (value) => {
    setValue(value);
  };

  const reset = () => {

  };

  const submit = (value, error) => {
    window.location.href = '/#/deployUser/deployPFA'
    console.log('error', error, 'value', value);
    if (error) {
      // 处理表单报错
    }
    // 提交当前填写的数据
  };

  return (
    <div className="create-activity-form">
      <IceContainer title="" className={styles.container}>
        <Form
          value={value}
          onChange={onFormChange}
        >
            {/* <FormItem {...formItemLayout} label="Private Key："
              required
              requiredMessage="Private Key cannot be empty!"
            >
              <Input name="name_sk" className={styles.inputWidth} />
            </FormItem> */}

            {/* <FormItem {...formItemLayout} label="Public Key："
              // required
              // requiredMessage="ID Key cannot be empty!"
            >
              <Input name="name_pk" className={styles.inputWidth} />
            </FormItem> */}

            {/* <FormItem {...formItemLayout} label="Authority Level：">
              <Select
                name="area"
                dataSource={[
                  { label: 'common_user', value: 'location1' },
                  { label: 'miner', value: 'location2' },
                  { label: 'validator', value: 'location3' },
                ]}
              />
            </FormItem> */}

            <FormItem {...formItemLayout} label=" ">
              <Form.Submit type="primary" validate onClick={submit}>
                Go Back
                </Form.Submit>
              {/* <Form.Reset className={styles.resetBtn} onClick={reset}>
                Reset
                </Form.Reset> */}
            </FormItem>
        </Form>
      </IceContainer>
    </div>
  );
}
