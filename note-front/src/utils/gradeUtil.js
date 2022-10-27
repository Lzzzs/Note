/**
 * 目前只有1,2,3 总共三个等级，等级容量100 -> 500 -> 1000
 */

const gradeMap = new Map();
gradeMap.set(1, 100);
gradeMap.set(2, 500);
gradeMap.set(3, 1000);

// 获取当前等级的容量
export function getCurrentGradeContainer(currentGrade) {
  return gradeMap.get(currentGrade);
}
