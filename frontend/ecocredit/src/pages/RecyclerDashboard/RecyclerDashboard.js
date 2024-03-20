import React from "react";
import ProfilePage from "../ProfilePage/ProfilePage";
import Dashboard from "../../components/Dashboard";
import { faCommentsDollar, faIdCard } from "@fortawesome/free-solid-svg-icons";
import ActiveBids from "../ActiveBids/ActiveBids";

const RecyclerDashboard = () => {
  const tabs = [
    {
      label: "Active Bids",
      to: "/recycler/active-bids",
      path: "/active-bids",
      icon: faCommentsDollar,
      component: <ActiveBids />,
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

export default RecyclerDashboard;
