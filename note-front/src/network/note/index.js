import instance from '../index'

export function saveNoteData(data) {
  return instance.post('/note/saveNoteData', data)
}

export function getNotes(page, size, tag = '') {
  let base = `/note/getNotes?page=${page}&size=${size}`
  if (tag != '') {
    base += `&tag=${tag}`
  }
  return instance.get(base)
}

export function getNoteByNoteId(id) {
  return instance.get(`/note/getNoteByNoteId?id=${id}`)
}

export function saveNoteContentImage(data) {
  return instance.post('/note/saveNoteContentImage', data)
}

export function searchByLike(page, size, searchValue) {
  return instance.get(
    `/note/searchBylike?page=${page}&size=${size}&searchValue=${searchValue}`
  )
}

export function getUserNoteAllRelation(userId, noteId) {
  return instance.get(
    `/note/getUserNoteAllRelation?userId=${userId}&noteId=${noteId}`
  )
}

/**
 * 收藏和点赞的添加和删除
 */
export function insertDianzanOne(data) {
  return instance.post('/note/insertDianzanOne', data)
}
export function insertCollectOne(data) {
  return instance.post('/note/insertCollectOne', data)
}
export function deleteDianzanOne(data) {
  return instance.post('/note/deleteDianzanOne', data)
}
export function deleteCollectOne(data) {
  return instance.post('/note/deleteCollectOne', data)
}

export function getCommentByNoteId(noteId) {
  return instance.get(`/note/getCommentByNoteId?noteId=${noteId}`)
}
export function getCommentLikeRelation(userId, noteId) {
  return instance.get(
    `/note/getCommentLikeRelation?userId=${userId}&noteId=${noteId}`
  )
}

export function addDianzanComment(data) {
  return instance.post('/note/addDianzanComment', data)
}
export function reduceDianzanComment(data) {
  return instance.post('/note/reduceDianzanComment', data)
}
export function saveComment(data) {
  return instance.post('/note/saveComment', data)
}

export function deleteCommentById(id) {
  return instance.post('/note/deleteCommentById', id)
}
