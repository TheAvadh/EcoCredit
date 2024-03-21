import React from "react";
import PickupSchedule from "../PickupSchedule/PickupSchedule";
import PickupStatus from "../PickupStatus/PickupStatus";
import ProfilePage from "../ProfilePage/ProfilePage";
import Dashboard from "../../components/Dashboard";
import {
  faCalendarCheck,
  faClockRotateLeft,
  faIdCard,
} from "@fortawesome/free-solid-svg-icons";

const CustomerDashboard = () => {
  const tabs = [
    {
      label: "Pickup Schedule",
      to: "/customer/pickup-schedule",
      path: "/pickup-schedule",
      icon: faCalendarCheck,
      component: <PickupSchedule />,
    },
    {
      label: "Pickup Status",
      to: "/customer/pickup-status",
      path: "/pickup-status",
      icon: faClockRotateLeft,
      component: <PickupStatus />,
    },
    {
      label: "Profile",
      to: "/profile-page",
      path: "/profile-page",
      icon: faIdCard,
      component: <ProfilePage />,
    },
  ];

  return <Dashboard tabs={tabs} />;
};

export default CustomerDashboard;
