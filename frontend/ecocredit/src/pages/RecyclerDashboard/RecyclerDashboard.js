import React from "react";
import ProfilePage from "../ProfilePage/ProfilePage";
import Dashboard from "../../components/Dashboard";
import { faIdCard } from "@fortawesome/free-solid-svg-icons";

const RecyclerDashboard = () => {
  const tabs = [
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

export default RecyclerDashboard;
