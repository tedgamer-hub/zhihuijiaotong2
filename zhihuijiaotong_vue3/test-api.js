const url = 'http://120.46.139.74:8080/IntelliDetect/api/v1/accidents';

fetch(url)
  .then(response => response.json())
  .then(data => console.log(JSON.stringify(data, null, 2)))
  .catch(error => console.error('Error:', error));