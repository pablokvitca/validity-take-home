import React, { Component } from 'react';
import { postCSV } from "../actions/helloAction";

class Hello extends Component {
  constructor(props) {
    super(props);
    this.state = {
      file: null
    };
    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.fileUpload = this.fileUpload.bind(this);
  }

  onFormSubmit(e){
    e.preventDefault(); // Stop form submit
    this.fileUpload(this.state.file).then((response) => {
      console.log(response.data);
    });
  }

  onChange(e) {
    this.setState({ file: e.target.files[0] });
  }

  fileUpload(file) {

    const formData = new FormData();
    formData.append('file', file);
    return postCSV(formData);
  }

  render() {
    return (
      <form onSubmit={this.onFormSubmit}>
        <h1>CSV Upload</h1>
        <input type="file" onChange={this.onChange} accept=".csv" />
        <button type="submit">Upload</button>
      </form>
    );
  }
}

export default Hello;
