package ar.uba.fi.armariovirtual.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import app.ArmarioVirtualApplication;
import ar.uba.fi.armariovirtual.R;

public class ImageUtils {

    public static Bitmap combinarMosaicos(Bitmap bitmaps[], int anchoTotal, int altoTotal)
    {
        int colorFondo = ContextCompat.getColor(ArmarioVirtualApplication.getAppContext(), R.color.colorFondoImagenCombinadaConjunto);
        int bordeImagenesDip = 10;

        Resources r = ArmarioVirtualApplication.getAppContext().getResources();
        int bordeImagenesPx = Math.round(
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        bordeImagenesDip,
                        r.getDisplayMetrics())
        );

        boolean imagenVertical = (altoTotal > anchoTotal);

        int numImagenes = bitmaps.length;
        double sqrtImagenes = Math.sqrt(numImagenes);

        int numMosaicosLadoCorto = (int) Math.floor(sqrtImagenes);
        int numMosaicosLadoLargo = (int) Math.ceil(sqrtImagenes);

        int numFilas = imagenVertical ? numMosaicosLadoLargo : numMosaicosLadoCorto;
        int numColumnas = imagenVertical ? numMosaicosLadoCorto : numMosaicosLadoLargo;

        int altoSinBordes = altoTotal - (numFilas + 1) * bordeImagenesPx;
        int anchoSinBordes = anchoTotal - (numColumnas + 1) * bordeImagenesPx;
        int ladoMosaico = imagenVertical ? altoSinBordes / numFilas : anchoSinBordes / numColumnas;

        Bitmap imagenFinal = Bitmap.createBitmap(anchoTotal, altoTotal, Bitmap.Config.ARGB_8888);
        Canvas imagenCanvas = new Canvas(imagenFinal);
        imagenCanvas.drawARGB(255, Color.red(colorFondo), Color.green(colorFondo), Color.blue(colorFondo));

        for(int i = 0; i < bitmaps.length; i++)
        {
            int columna = i % numColumnas;
            int fila    = i / numColumnas;
            Bitmap bitmap = bitmaps[i];

            // TODO: Centrar y escalar imágenes no cuadradas
            int anchoOriginal = bitmap.getWidth();
            int altoOriginal = bitmap.getHeight();

            float fitScaleX = (float)ladoMosaico / (float)anchoOriginal;
            float fitScaleY = (float)ladoMosaico / (float)altoOriginal;
            float scale = Math.min(fitScaleX, fitScaleY);

            int anchoEscalado = (int) (anchoOriginal * scale);
            int altoEscalado = (int) (altoOriginal * scale);

            float offsetX = (float) ((ladoMosaico - anchoOriginal * scale) * 0.5);
            float offsetY = (float) ((ladoMosaico - altoOriginal * scale) * 0.5);

            int x = columna * ladoMosaico + Math.round(offsetX) + (columna + 1) * bordeImagenesPx;
            int y = fila * ladoMosaico + Math.round(offsetY) + (fila + 1) * bordeImagenesPx;

            imagenCanvas.drawBitmap(bitmap, null, new Rect(x, y, x + anchoEscalado, y + altoEscalado), null);
        }
        return imagenFinal;
    }

    public static void ajustarImagenEnRoundedImageView(Context context, ImageView imageView, String uriImagen) {

        ajustarImagenEnRoundedImageView(context, imageView, Uri.parse(uriImagen));
    }

    public static void ajustarImagenEnRoundedImageView(Context context, ImageView imageView, Uri uriImagen) {
        if (uriImagen == null || imageView == null)
            return;

        Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.BLACK).borderWidthDp(2).cornerRadiusDp(15).oval(false).build();
        Picasso.with(context).load(uriImagen).fit().centerInside()/*.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)*/.transform(transformation).into(imageView);

        imageView.setVisibility(View.VISIBLE);
    }

    public static void ajustarImagenEnImageView(Context context, ImageView imageView, String uriImagen) {
        if (uriImagen == null || uriImagen.isEmpty() || imageView == null)
            return;

        Picasso.with(context).load(uriImagen).fit().centerInside().into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }

}
