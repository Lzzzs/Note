import instance from '../index.js'

export function addGroup(groupInfo) {
  return instance.post('/group/addGroup', groupInfo)
}
export function selectAllOrganize() {
  return instance.get('/group/selectAllOrganize')
}
export function selectOrganizeByUserId(userId) {
  return instance.get(`/group/selectOrganizeByUserId?userId=${userId}`)
}
export function selectOrganizeById(id) {
  return instance.get(`/group/selectOrganizeById?id=${id}`)
}
export function selectOrganizeUserById(id) {
  return instance.get(`/group/selectOrganizeUserById?id=${id}`)
}
export function getOrganizeNotes(id, page, size, tag = '') {
  let base = `/group/getOrganizeNotes?id=${id}&page=${page}&size=${size}`
  if (tag != '') {
    base += `&tag=${tag}`
  }
  return instance.get(base)
}

export function addOrganizeUser(organizeInfo) {
  /**
   * {
   *    id: organize的id
   *    userId
   * }
   */
  return instance.post('/group/addOrganizeUser', organizeInfo)
}
