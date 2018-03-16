package lll.ssm.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RandomValidateCode {
    private Random random = new Random();
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// ����������ַ���

    private int width = 80;// ͼƬ��
    private int height = 26;// ͼƬ��
    private int lineSize = 40;// ����������
    private int stringNum = 4;// ��������ַ�����

    /*
     * �������
     */
    private Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }

    /*
     * �����ɫ
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /*
     * �����ַ���
     */
    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString
                .length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    /*
     * ���Ƹ�����
     */
    private void drowLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /*
     * ��ȡ������ַ�
     */
    public String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }


    /**
     * �������ͼƬ
     */
    public void getRandcode(HttpServletRequest request,HttpServletResponse response,String key) {

        // BufferedImage���Ǿ��л�������Image��,Image������������ͼ����Ϣ����
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// ����Image�����Graphics����,�Ķ��������ͼ���Ͻ��и��ֻ��Ʋ���
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(getRandColor(110, 133));
        // ���Ƹ�����
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g);
        }
        // ��������ַ�
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = drowString(g, randomString, i);
        }
        //1����������ɵ���֤�����Cookie��
        Cookie cookie = new Cookie(key,randomString);
        response.addCookie(cookie);
        //2����������ɵ���֤�����session��
        String sessionid = request.getSession().getId();
        request.getSession().setAttribute(sessionid+key, randomString);
        System.out.println("*************" + randomString);

        //�ܽ᣺�����ַ�ʽ�����Ǻܺã�
        //��1����ʹ��cookie�ķ�ʽ������֤�뷢�͵�ǰ̨�����������ȫ��������ʹ�á�
        //��2����ʹ��session�ķ�ʽ����Ȼ�ܽ����֤�벻���͵����������ȫ�Խϸ��ˣ���������û���̫�������Ĵ洢��ʽ��Է��������ѹ����Ӱ������������ܡ�������ʹ�á�
        //������ʱʵ�������ַ�ʽ���õİ취�ǣ�����Ŀ��ʹ�õĻ��棬�����ɵ���֤���ŵ������У�����ʧЧʱ�䣬�����ȿ���ʵ�ְ�ȫ��Ҳ�ܼ����������ѹ����
        g.dispose();
        try {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            ImageIO.write(image, "png", tmp);
            tmp.close();
            Integer contentLength = tmp.size();
            response.setHeader("content-length", contentLength + "");
            response.getOutputStream().write(tmp.toByteArray());// ���ڴ��е�ͼƬͨ��������ʽ������ͻ���
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
