import axios from "axios";

const authToken = (token) => {
  if (token) {
    axios.defaults.headers.common["Bearer "] = `${token}`;
  } else {
    delete axios.defaults.headers.common["Bearer "];
  }
};

export default authToken;
