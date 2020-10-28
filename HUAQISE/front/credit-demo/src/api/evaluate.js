import { axios } from '@/utils/request'

const api = {
    evaluatePre:'/epservice/api/index'
}




export function upload_indexAPI(id, div, fin) {//上传指标
    console.log('id' , id)
    console.log('div' , div)
    console.log('fin' , fin)
    return axios({
        url: `${api.evaluatePre}/11/add_target`,
        method: 'post',
        data: {
            "div": [1.0, 2.0, 3.0],
            "fin": [1.1]
        }
    })

}

export function get_enterprise_indexAPI(id){

    // return axios({
    //     url : `${api.evaluatePre}/${id}/get_target`,
    //     method : 'GET'
    // })
}


