import { ResponsiveBar } from '@nivo/bar';
import { useDashboardContext } from '../Dashboard';
import Spinner from '../Spinner/Spinner';
import './RealTimeEmotion.css';

/**
 *
 * @returns Returns a Bar Chart Component with the percentages of each emotion.
 */
const RealTimeEmotion = () => {
  const { currentExpression, mountedVideoComponent } = useDashboardContext();

  /**
   *
   * @param {object} data - Data of the expression which was hovered.
   * @returns {HTMLSpanElement} - Reutrns a <span> with the name of the expression which was hovered.
   */
  // const getTooltip = (data) => {
  //   // should only return HTML
  //   return (
  //     <span className="tooltip bg-bg-1 rounded-md text-xs p-1 border-solid border-gray-500 border-2">{`${
  //       data.data.expression
  //     }: ${Math.round(data.data.percent)}%`}</span>
  //   );
  // };

  return currentExpression !== null && currentExpression !== undefined ? (
    <ResponsiveBar
      theme={{
        axis: {
          ticks: {
            text: {
              fill: '#FDFEFE',
            },
          },
          legend: {
            text: {
              fill: '#FDFEFE',
            },
          },
        },
      }}
      data={currentExpression}
      keys={['percent']}
      indexBy={'expression'}
      layout={'vertical'}
      margin={{ top: 10, right: 120, bottom: 50, left: 30 }}
      borderRadius={7}
      padding={0.4}
      maxValue={100}
      minValue={0}
      valueScale={{ type: 'linear' }}
      colors="#FAF2FF"
      animate={true}
      enableLabel={false}
      axisTop={null}
      axisRight={null}
      axisLeft={{
        tickSize: 5,
        tickPadding: 5,
        tickRotation: 0,
        legend: '수치',
        legendPosition: 'middle',
        legendOffset: -40,
      }}
      axisBottom={{
        legend: '감정',
        legendPosition: 'middle',
        legendOffset: 40,
      }}
      // tooltip={(data) => getTooltip(data)}
    />
  ) : (
    <Spinner text={'녹화를 시작하면 그래프가 나옵니다.'} />
  );
};

export default RealTimeEmotion;
