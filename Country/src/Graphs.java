import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;
import java.io.IOException;

public class Graphs {

    public static CategoryDataset createDataset(double[] values, String[] keys)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (var i=0; i < values.length;i++){
            dataset.addValue(values[i], keys[i], "Subregions");
        }

        return dataset;
    }
    public static void createChart(CategoryDataset dataset)
    {
        JFreeChart chart = ChartFactory.createBarChart(
                "Percent users in subregions",
                null,
                "Percent",
                dataset);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        try {
            ChartUtils.saveChartAsPNG(new File("chart.png"), chart, 800, 720);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
