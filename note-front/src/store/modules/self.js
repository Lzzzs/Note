export default {
  state: () => ({
    deleteNotes: []
  }),
  mutations: {
    SET_DELETE_NOTES(state, newDeleteNotes) {
      state.deleteNotes = newDeleteNotes
    }
  }
}
