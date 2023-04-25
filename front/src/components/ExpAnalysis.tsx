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
  labels: ["Commit", "Issue", "Merge", "Review", "Efficiency", "Security"],
  datasets: [
    {
      label: "EXP",
      data: [5, 7, 5, 9, 7, 4],
      backgroundColor: "rgba(255, 255, 0, 0.2)",
      borderColor: "yellow",
      pointBackgroundColor: "rgb(255, 255, 255)",
      borderWidth: 1.5,
      color: "white",
    },
  ],
};

export default function ExpAnalysis() {
  return (
    <Radar
      data={data}
      options={{
        maintainAspectRatio: false,
        scales: {
          r: {
            angleLines: {
              color: "rgba(255, 255, 255, 0.8)",
            },
            grid: {
              color: "rgba(255, 255, 255, 0.8)",
            },
            pointLabels: {
              font: {
                family: "SUIT-Thin",
                size: 15,
              },
              color: "white",
            },
            ticks: {
              color: "black",
            },
          },
        },
        plugins: {
          title: {
            color: "white",
          },
          legend: {
            position: "right",
            labels: {
              color: "white",
              font: {
                family: "SUIT-Thin",
                size: 16,
              },
            },
          },
        },
      }}
    />
  );
}
