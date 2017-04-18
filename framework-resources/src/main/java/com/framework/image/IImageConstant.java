package com.framework.image;

public interface IImageConstant {

	public static final int VIEW_SIZE = 100;
	public static final int LIST_SIZE = 100;
	public static final int SMALL_SIZE = 100;
	public static final int ROL_SIZE = 100;

	public static final String ADAPT = "7";
	public static final String QUALITY = "75";
	public static final String COMPRE = "7";

	public static final int MAX_DETAIL = 100;
	public static final int MAX_SIZE = 1080;
	public static final int WATERMARKDISSOLVE = 40;
	public static final int WATERMARKMARGIN = 20;

	// 图片质量
	public static final String IMAGE_QUALITY = "quality";
	// 图片高度
	public static final String IMAGE_HEIGHT = "height";
	// 图片宽度
	public static final String IMAGE_WIDTH = "width";
	// 图片格式
	public static final String IMAGE_SUFFIX = "suffix";
	// 图片大小
	public static final String IMAGE_SIZE = "size";
	// 图片路径
	public static final String IMAGE_PATH = "path";

	public enum WartermarkPosition {

		POS_LT(0), POS_RT(1), POS_LB(2), POS_RB(3), POS_ALL(4);

		private int value = 0;

		private WartermarkPosition(int value) {
			this.value = value;
		}

		public static WartermarkPosition valueOf(int value) {
			switch (value) {
			case 0:
				return POS_LT;
			case 1:
				return POS_RT;
			case 2:
				return POS_LB;
			case 3:
				return POS_RB;
			case 4:
				return POS_ALL;
			}
			return POS_RB;
		}
	}
}
