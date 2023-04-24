import React from "react";
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from "chart.js";
import { Radar } from "react-chartjs-2";

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

const data = {
  labels: ["Commit", "Issue", "Merge", "Review", "Efficency", "Security"],
  datasets: [
    {
      label: "EXP",
      data: [5, 7, 5, 9, 7, 4],
      backgroundColor: "rgba(255, 99, 132, 0.2)",
      borderColor: "rgba(255, 99, 132, 1)",
      borderWidth: 1,
    },
  ],
};

export default function RadarChart() {
  return (
    <Radar
      data={data}
      width={200}
      height={200}
      options={{
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: "right",
          },
        },
      }}
    />
  );
}
