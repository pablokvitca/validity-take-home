import axios from 'axios';

export async function postCSV(formdata) {
  return (await axios({
                        method: "post",
                        url: "http://localhost:8080/api/parse-duplicate-contact",
                        data: formdata,
                        headers: {'Content-Type': 'multipart/form-data' }
                      }));
}
