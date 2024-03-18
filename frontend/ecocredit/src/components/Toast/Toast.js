import React from "react";
import Toast from "react-bootstrap/Toast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSquareCheck,
  faSquareXmark,
} from "@fortawesome/free-solid-svg-icons";
import "./Toast.css";

const ProfileToast = ({ showToast, setShowToast, toastMessage, toastType }) => {
  const getToastIcon = (type) => {
    switch (type) {
      case "success":
        return (
          <FontAwesomeIcon
            icon={faSquareCheck}
            className="text-ec-dark-green me-2"
          />
        );
      case "error":
        return (
          <FontAwesomeIcon
            icon={faSquareXmark}
            className="text-ec-dark-green me-2"
          />
        );
      default:
        return null;
    }
  };

  return (
    <Toast
      onClose={() => setShowToast(false)}
      show={showToast}
      delay={4000}
      autohide
      position="bottom-end"
      className="toast-position"
    >
      <Toast.Header className="bg-ec-grey">
        <strong className="me-auto text-ec-dark-green">Notification</strong>
      </Toast.Header>
      <Toast.Body className="text-ec-dark-green">
        {getToastIcon(toastType)}
        {toastMessage}
      </Toast.Body>
    </Toast>
  );
};

export default ProfileToast;
