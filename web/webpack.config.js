const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    createGoal: path.resolve(__dirname, 'src', 'pages', 'createGoal.js'),
    home: path.resolve(__dirname, 'src', 'pages', 'home.js'),
    viewAllGoals: path.resolve(__dirname, 'src', 'pages', 'viewAllGoals.js'),
    about: path.resolve(__dirname, 'src', 'pages', 'about.js'),
    userDashboard: path.resolve(__dirname, 'src', 'pages', 'userDashboard.js'),
    updateGoalPriority: path.resolve(__dirname, 'src', 'pages', 'updateGoalPriority.js'),
    updateGoalAmount: path.resolve(__dirname, 'src', 'pages', 'updateGoalAmount.js'),
    updateGoalDescription: path.resolve(__dirname, 'src', 'pages', 'updateGoalDescription.js'),
    updateGoalStatus: path.resolve(__dirname, 'src', 'pages', 'updateGoalStatus.js'),
    deleteGoal: path.resolve(__dirname, 'src', 'pages', 'deleteGoal.js'),
    getGoalByCategory: path.resolve(__dirname, 'src', 'pages', 'getGoalByCategory.js'),
    getGoalById: path.resolve(__dirname, 'src', 'pages', 'getGoalById.js'),
    getGoalByName: path.resolve(__dirname, 'src', 'pages', 'getGoalByName.js')
  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}
