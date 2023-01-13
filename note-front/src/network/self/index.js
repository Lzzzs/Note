import instance from '../index.js'

export function getGradeInfo(userId) {
  return instance.get(`/self/getGradeInfo?userId=${userId}`)
}
export function getPointInfo(userId) {
  return instance.get(`/self/getPointInfo?userId=${userId}`)
}

export function exchangeAward(userId, awardPoint) {
  return instance.get(
    `/self/exchangeAward?userId=${userId}&awardPoint=${awardPoint}`
  )
}

export function getUserAllNotesByUserId(page, size, userId) {
  return instance.get(
    `/self/getUserAllNotesByUserId?page=${page}&size=${size}&userId=${userId}`
  )
}
export function getUserAllCollectNotesByUserId(page, size, userId) {
  return instance.get(
    `/self/getUserAllCollectNotesByUserId?page=${page}&size=${size}&userId=${userId}`
  )
}
export function deleteNotes(noteInfo) {
  return instance.post('/self/deleteNotes', noteInfo)
}
