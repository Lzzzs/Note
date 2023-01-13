import Vue from 'vue'
import {
  Button,
  Header,
  Footer,
  Container,
  Main,
  Image,
  Avatar,
  Carousel,
  CarouselItem,
  Form,
  FormItem,
  Input,
  Link,
  Breadcrumb,
  BreadcrumbItem,
  Scrollbar,
  Progress,
  Message,
  Select,
  Option,
  Upload,
  Empty,
  Pagination,
  Skeleton,
  SkeletonItem,
  MessageBox,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  Drawer,
  Dialog,
  Checkbox
} from 'element-ui'

Vue.use(Button)
Vue.use(Header)
Vue.use(Footer)
Vue.use(Container)
Vue.use(Main)
Vue.use(Image)
Vue.use(Avatar)
Vue.use(Carousel)
Vue.use(CarouselItem)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Link)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(Scrollbar)
Vue.use(Progress)
Vue.use(Select)
Vue.use(Option)
Vue.use(Upload)
Vue.use(Empty)
Vue.use(Pagination)
Vue.use(Skeleton)
Vue.use(SkeletonItem)
Vue.use(Dropdown)
Vue.use(DropdownItem)
Vue.use(DropdownMenu)
Vue.use(Drawer)
Vue.use(Dialog)
Vue.use(Checkbox)

Vue.prototype.$message = Message
Vue.prototype.$confirm = MessageBox.confirm
