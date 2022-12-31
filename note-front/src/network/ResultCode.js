/**
 * 错误代码是code
 * errMessage是错误代码对应的信息
 * to是要发生该错误时需要跳转的url，可选参数
 *
 * codeMap
 *    code: [errMessage, to？]
 */
const codeMap = new Map();

const codes = [
  [20001, "用户未登录，请先登录", "/login"],
  [50004, "笔记不存在", "/"],
];

for (const item of codes) {
  codeMap.set(item[0], [item[1], item[2]]);
}

export default codeMap;
