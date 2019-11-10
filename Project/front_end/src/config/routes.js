import HomePage from '@/pages/HomePage';
import BlankLayout from '@/layouts/BlankLayout';
import Login from '@/pages/Login';
import DeployEoa from '@/pages/DeployEoa';
import DeployPfa from '@/pages/DeployPfa';
import DeployItems from '@/pages/DeployItems';
import EoaSuccess from '@/pages/EoaSuccess';
import PfaSuccess from '@/pages/PfaSuccess';
import EventSuccess from '@/pages/EventSuccess';
import GetInfo from '@/pages/GetInfo';
import DiaLog from '@/pages/DiaLog';

const routerConfig = [
  {
    path: '/',
    component: BlankLayout,
    children: [
      { path: '/Login', component: Login },
      { path: '/deployUser/deployEOA', component: DeployEoa },
      { path: '/deployUser/deployPFA', component: DeployPfa },
      { path: '/deployBlock/deployItems', component: DeployItems },
      { path: '/deployUser/deploySuccessEOA', component: EoaSuccess },
      { path: '/deployUser/deploySuccessPfa', component: PfaSuccess },
      { path: '/proposeEventSuccess', component: EventSuccess },
      { path: '/getInfo/T8Nye', component: DiaLog },
      { path: '/getInfo', component: GetInfo },
      { path: '/', component: HomePage },
    ],
  },
];

export default routerConfig;
