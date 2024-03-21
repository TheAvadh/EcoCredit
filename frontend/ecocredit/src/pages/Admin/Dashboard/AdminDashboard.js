import Dashboard from "../../../components/Dashboard";
import {
    faCalendarCheck,
    faCheck
  } from "@fortawesome/free-solid-svg-icons";
import ScheduledPickups from "../Pickup/Scheduled/ScheduledPickups";
import CompletedPickups from "../Pickup/Completed/CompletedPickups";

const AdminDashboard = () => {
    const tabs = [
        {
            label: "Scheduled Pickups",
            to: "/admin/scheduled-pickups",
            path: "/scheduled-pickups",
            icon: faCalendarCheck,
            component: <ScheduledPickups />,
          },
        {
          label: "Completed Pickups",
          to: "/admin/completed-pickups",
          path: "/completed-pickups",
          icon: faCheck,
          component: <CompletedPickups />,
        }
    ];

    return <Dashboard tabs={tabs} />;
};

export default AdminDashboard;