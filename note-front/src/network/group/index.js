import instance from '../index.js'

export function addGroup(groupInfo) {
  return instance.post('/group/addGroup', groupInfo)
}
